import pandas as pd
import pickle
import os

def obtain_params_opt() :
    
    path = os.path.join(os.path.dirname(__file__), "params", "params_opt.pkl")

    with open(path, "rb") as file :
        a = pickle.load(file)
        
    path = os.path.join(os.path.dirname(__file__), "params", "columns.pkl")

    with open(path, "rb") as file :
        columns = pickle.load(file)
    
    return a, columns

def format_data(csv_file, columns) :
    
    df = pd.read_csv(csv_file.name)
    
    df = df.drop(columns=['Unnamed: 0', 'flight'])
    df['class'] = df['class'].apply(lambda x: 1 if x=='Business' else 0)
    df.stops = pd.factorize(df.stops)[0]
    
    for col in ['airline', 'source_city', 'destination_city', 'departure_time', 'arrival_time']:
        counts = df[col].value_counts()
        common = counts[counts > 100].index  
        df[col] = df[col].where(df[col].isin(common), other='Other')

    df = pd.get_dummies(df, columns=[
        'airline', 'source_city', 'destination_city',
        'departure_time', 'arrival_time', 'stops', 'class'
    ], drop_first=True)
    
    df = df.drop_duplicates()
    
    x = df.copy()
    
    x.insert(0, 'intercept', 1)
    
    for col in columns:
        if col not in df.columns:
            df[col] = 0 
            
    df = df[columns]

    X_matrix = df.astype(float).to_numpy()
    
    return X_matrix