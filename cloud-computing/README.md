# Write Application in flask & TensorFlow

# Setup your google cloud
Create new project
activate cloud run API and cloud builds API

# Install and init google cloud sdk
https://cloud.google.com/sdk/docs/install

# Dockerfile, requirements.txt, .dockerignore
https://cloud.google.com/run/docs/quickstart/build-and-deploy#containerizing

# Cloud build and deploy
gcloud builds submit --tag gcr.io/bangkit-project-386513/index
gcloud run deploy --image gcr.io/bangkit-project-386513/index --platform managed