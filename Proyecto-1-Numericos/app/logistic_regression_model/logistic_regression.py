from .utils import obtain_files_model, load_image, predict_class
from PIL import Image
import numpy as np

def classify_flowers(image) :
    
    classifiers, scaler, encoder = obtain_files_model()
    
    if isinstance(image, np.ndarray) :
        image = Image.fromarray(image.astype("uint8"))
    
    X = load_image(image)
    X_scaled = scaler.transform(X)
    
    predicted_class = predict_class(X_scaled, classifiers, encoder)
    return predicted_class