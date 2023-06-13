import os
import io
import tensorflow as tf
from tensorflow import keras
import numpy as np
from PIL import Image

#implement in the app
from flask import Flask, request, jsonify

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

model = tf.lite.Interpreter(model_path="base_model_trimmed.tflite")
model.allocate_tensors()
input_index = model.get_input_details()[0]['index']
output_index = model.get_output_details()[0]['index']

def transform_image(pillow_image):
    pillow_image = pillow_image.resize((224, 224))  # Resize the image to (224, 224)
    pillow_image = pillow_image.convert('RGB')  # Convert the image to RGB mode
    data = np.asarray(pillow_image)
    data = data / 255.0
    data = data.astype('float32')  # Convert the data to float32
    data = data[np.newaxis, ...]  # Remove the last axis
    return data

def predict(x, class_labels):
    model.set_tensor(input_index, x)
    model.invoke()
    predictions = model.get_tensor(output_index)
    predicted_class_index = np.argmax(predictions[0])
    predicted_label = class_labels[predicted_class_index]
    prediction_scores = predictions[0].tolist()
    return predicted_label, prediction_scores

app = Flask(__name__)

@app.route("/predict", methods=["GET", "POST"])
def index():
    class_labels = ['Glass_ClearGlass', 'Metal_AluminiumCans', 'Paper_Paper', 'Plastic_PET', 'Textiles_Textiles']

    if request.method == "POST":
        file = request.files.get('file')
        if file is None or file.filename == "":
            return jsonify({"error": "no file"})

        try:
            image_bytes = file.read()
            pillow_image = Image.open(io.BytesIO(image_bytes)).convert('L')
            tensor = transform_image(pillow_image)
            predicted_label, prediction_scores = predict(tensor, class_labels)

            data = {
                "filename": file.filename,
                "predicted_class": predicted_label,
                "prediction_scores": prediction_scores[0]
            }
            return jsonify(data)
        except Exception as e:
            return jsonify({"error": str(e)})

    return "OK"


if __name__ == "__main__":
    app.run(debug=True)