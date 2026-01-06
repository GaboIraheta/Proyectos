import pandas as pd
import numpy as np
from mult_regression_model.utils import obtain_params_opt, format_data

def predict_prices(csv_file) :
    
    a, columns = obtain_params_opt()
    
    X = format_data(csv_file, columns)

    y_pred = X @ a
    y_pred = y_pred.flatten()
    
    df_result = pd.DataFrame({
        "n" : np.arange(1, len(y_pred) + 1),
        "Precio predicho" : y_pred.round(2)
    })
    
    return df_result