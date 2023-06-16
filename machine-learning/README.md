<h1>Machine Learning Implementation</h1>
The ML path part of this app is categorizing uploaded images of recyclables into specific emission factors. The emission factor is how much carbon footprint is emitted per specific mass, which is written as CO2eq/kg. The data is gathered from reliable source which taken from a journal articles. This parameter differs between each recyclable category, therefore the ML part is to categorize the multiclass classification for the image.

<h1>Reference (Emission Factor)</h1>
Turner, D. A., Williams, I. D., & Kemp, S. (2015). Greenhouse gas emission factors for recycling of source-segregated waste materials. Resources, Conservation and Recycling, 105, 186–197. https://doi.org/10.1016/j.resconrec.2015.10.026 
<h1>Reference (Dataset)</h1>
https://www.kaggle.com/datasets/asdasdasasdas/garbage-classification<br>
https://www.kaggle.com/datasets/yacharki/manon-str-cleaned-dataset<br>
https://www.kaggle.com/datasets/mrk1903/garbage<br>
For more references, refer to: https://docs.google.com/spreadsheets/d/1fcXfXuKJbmPWHtu7D_Wj8RUo2sy0pk_yDTAzwUaN95s/edit#gid=0

<h1>Note for Adjustment</h1>
According to the timeline, the current version of ML is simplified into 5 categories to the planned 17 categories to ensure functionality and to test the deployment faster. The categories we included in the first phase are Glass_ClearGlass; Metal_AluminiumCans; Paper_Paper; Plastic_PET; Textiles_Textiles.
We will gradually increase the number of categories to ensure the accuracy of our model.

<h1>Modules</h1>
TensorFlow
NumPy
OS
Cv2
Random
Matplotlib

<h1>Steps Documentation</h1>
Below explained our run-through on what we do in our code.

First, we mount our code to get access to google drive. After that, we import all the dependencies we need. Then, we extract the dataset with unzip command, so that it can be used in the model. After we extract the dataset, we define the base directory. Then, we use ImageDataGenerator to perform augmentation on the images. We split 10% of the images for validation data. After that, we index the classes.

Here, we create the architecture for our model. We use a CNN model with three convolutional layers, a dropout layer, and the softmax activation function. Then, we compile the model using adam optimizer and set metrics to accuracy. We also create the summary of the model. After that, we set the callbacks. After setting the callbacks, it's time to train our model. After training the model, we plot our model results. Finally, we make predictions for our model.

<h1>Deployment</h1>
For deployment, we use TensorFlow Lite because our model will be connected to the mobile application.

