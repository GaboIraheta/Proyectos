import numpy as np
import pickle
from .model.model import sigmoid
import os

def obtain_files_model() :
    
    path = os.path.join(os.path.dirname(__file__), "classifiers", "flowers_model.pkl")
    
    with open(path, "rb") as f:
        classifiers = pickle.load(f)
        
    path = os.path.join(os.path.dirname(__file__), "classifiers", "scaler.pkl")

    with open(path, "rb") as f:
        scaler = pickle.load(f)
        
    path = os.path.join(os.path.dirname(__file__), "classifiers", "encoder.pkl")

    with open(path, "rb") as f:
        encoder = pickle.load(f)
        
    return classifiers, scaler, encoder

def load_image(image, size=(64, 64)):

    img = image.resize(size)
    img_array = np.array(img).astype(np.float32) / 255.0  
    
    return img_array.flatten().reshape(1, -1) 

def predict_class(X, classifiers, encoder):
        
    m = X.shape[0]
    X = np.hstack([np.ones((m, 1)), X])
    
    num_classes = len(classifiers)
    
    probs = np.zeros((m, num_classes))
        
    for c, w in classifiers.items():
        
        z = np.dot(X, w)
        prob = sigmoid(z)
        
        probs[:, c] = prob

    max_prob = np.argmax(probs, axis=1)
    predicted_class = encoder.inverse_transform([max_prob])[0]
    
    return predicted_class