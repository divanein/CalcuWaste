import os
import io
import tensorflow as tf
from tensorflow import keras
import numpy as np
from PIL import Image
import pymysql
from nanoid import generate
from google.cloud import storage
from flask import Flask, request, jsonify

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

model = tf.lite.Interpreter(model_path="base_model_trimmed.tflite")
model.allocate_tensors()
input_index = model.get_input_details()[0]['index']
output_index = model.get_output_details()[0]['index']

class_labels = ['Glass_ClearGlass', 'Metal_AluminiumCans', 'Paper_Paper', 'Plastic_PET', 'Textiles_Textiles']
class_emissionFactor = [0.395, 0.883, 1.576, 0.339, 0.401]
class_price = [0.15, 1, 0.5, 0.425, 0.55]

def transform_image(pillow_image):
    pillow_image = pillow_image.resize((224, 224))
    pillow_image = pillow_image.convert('RGB')
    data = np.asarray(pillow_image)
    data = data / 255.0
    data = data.astype('float32')
    data = data[np.newaxis, ...]
    return data

def predict(x):
    model.set_tensor(input_index, x)
    model.invoke()
    predictions = model.get_tensor(output_index)
    predicted_class_index = np.argmax(predictions[0])
    predicted_label = class_labels[predicted_class_index]
    prediction_scores = predictions[0].tolist()
    return predicted_label, prediction_scores

def save_to_cloud_sql(data):
    connection = pymysql.connect(
        host='34.101.116.161',
        user='root',
        password='calcuwaste',
        database='calcuwaste',
        cursorclass=pymysql.cursors.DictCursor
    )

    try:
        with connection.cursor() as cursor:
            # Insert the data into the table
            sql = "INSERT INTO calculatedCalcuwaste (image_link, predicted_class, prediction_scores, emission_factor, price_rupiah, weight_gram, emission_result, selling_price) VALUES (%s, %s, %s, %s, %s, %s, %s, %s)"
            cursor.execute(sql, (
                data["image_link"],
                data["predicted_class"],
                data["prediction_scores"],
                data["emission_factor"],
                data["price_rupiah"],
                data["weight_gram"],
                data["emission_result"],
                data["selling_price"]
            ))
        connection.commit()
    finally:
        connection.close()

def save_to_bucket(file):
    # Generate a unique filename using nanoid
    unique_filename = generate(size=10)
    filename = f"{unique_filename}_{file.filename}"

    # Save the file to the bucket with the unique filename
    client = storage.Client()
    bucket = client.get_bucket('calcuwaste-savedpredictimage')
    blob = bucket.blob(filename)
    blob.upload_from_file(file)

    # Get the public URL of the uploaded file
    image_link = blob.public_url

    return image_link


app = Flask(__name__)

@app.route("/predict", methods=["POST"])
def index():
    if request.method == "POST":
        file = request.files.get('file')
        weight = int(float(request.form.get('weight')))

        if file is None or file.filename == "":
            return jsonify({"error": "no file"})

        try:
            # Save the image to the bucket
            image_link = save_to_bucket(file)

            pillow_image = Image.open(file).convert('L')
            tensor = transform_image(pillow_image)
            predicted_label, prediction_scores = predict(tensor)

            class_index = class_labels.index(predicted_label)
            emission_factor = class_emissionFactor[class_index]
            price = class_price[class_index]

            emission_result = weight * emission_factor
            selling_price = weight * price

            data = {
                "image_link": image_link,
                "predicted_class": predicted_label,
                "prediction_scores": prediction_scores[0],
                "emission_factor": emission_factor,
                "price_rupiah": price,
                "weight_gram": weight,
                "emission_result": int(emission_result),
                "selling_price": int(selling_price)
            }

            save_to_cloud_sql(data)

            return jsonify(data)
        except Exception as e:
            return jsonify({"error": str(e)})

    return "OK"

if __name__ == "__main__":
    app.run(debug=True)
