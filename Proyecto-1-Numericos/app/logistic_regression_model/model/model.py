import numpy as np

def sigmoid(z) :
    return 1 / (1 + np.exp(-z))

def predict_ovr(X, classifiers) :   
        
    m = X.shape[0] # numero de muestras
    print(f"Muestras -> {m}\n")
    
    X = np.hstack([np.ones((m, 1)), X]) # se agrega la columna de sesgo a las caracteristicas para tener igual dimension con los w optimizados
    print(f"Datos de entrada -> \n{X}\n")
    
    num_classes = len(classifiers) # numero de clasificadores (total de clases - 1 clasificador por cada clase)
    
    # matriz que almacena la probabilidad para cada muestra de pertenecer a cada clase
    
    probs = np.zeros((m, num_classes)) # (shape m x num_classes)
    print(f"Probabilidades -> \n{probs}\n")
    
    # calculando la probabilidad de pertenecer a cada clase usando los clasificadores entrenados
        
    for c, w in classifiers.items() :
                        
        print(f"Shape X: -> {X.shape}")
        print(f"Shape W: -> {w.shape}\n")
        
        print(f"Pesos -> \n{w}\n")
        
        z = np.dot(X, w) # se calcula el valor lineal z para todas las muestras, proyectando sobre W y agregado el sesgo 
        print(f"z -> \n{z}\n")

        # se calcula la probabilidad de cada muestra de pertenecer a la clase c generando un vector de probabilidades
        
        _sigmoid = sigmoid(z) # se aplica sigmoide para convertir el vector z en el vector de probabilidades
        print(f"Sigmoid -> \n{_sigmoid}\n")
        
        probs[:, c] = _sigmoid # se almacena el vector de probabilidades de cada muestra de pertenecer a la clase c
    
    print(f"All probabilities -> \n{probs}\n")
            
    # generando un vector con la clase predicha para cada muestra
    
    max_prob = np.argmax(probs, axis=1) # para cada muestra se toma la clase con mayor probabilidad
    print(f"max_prob -> {max_prob}")
            
    return max_prob # retorna lel vector de clases predichas para cada muestra
