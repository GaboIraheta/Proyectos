# importacion de librerias utilizadas
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

# definicion de los parametros requeridos (media poblacional y tamaño de muestra)
media = 120
n = 200

# muestra aleatoria que sigue una distribucion exponencial
muestra = np.random.exponential(scale=media, size=n)

# creacion del data frame para mostrar los primeros 20 valores aleatorios generados en la muestra
table = pd.DataFrame({
    'Dato' : range(1, 21),
    'Valor' : muestra[:20]
})

# estilo de la tabla
table_style = (table.style
                .format({"Valor": "{:.2f}"})
                .set_table_styles([{
                    'selector': 'th',
                    'props': [('background-color', 'transparent'),
                              ('color', 'black'),
                              ('border', '1px solid black'),
                              ('text-align', 'center')]
                }])
                .set_properties(**{'background-color': 'transparent',
                                   'color': 'black',
                                   'border': '1px solid black',
                                   'text-align': 'center'})
               )

# muestra la tabla con los primeros 20 valores
table_style

# se obtiene la media muestral
media_muestral = np.mean(muestra)
print(f"{media_muestral:.2f}")

# se obtiene el parametro lambda para la muestra
lambda_muestral = 1 / media_muestral
print(f"{lambda_muestral:.4f}")

# se muestra todos los parametros dados y obtenidos
print(f"Media poblacional teorica: {media}\n")
print(f"Parametro lambda poblacional: {(1 / 120):.4f}\n")
print(f"Tamaño de la muestra: {n}\n")
print(f"Media muestral: {media_muestral:.2f}\n")
print(f"Parametro lambda muestral: {lambda_muestral:.4f}\n")

# generacion del histograma
plt.hist(muestra, bins=20, color='skyblue', edgecolor='black')
plt.title("Distribucion de vida util de baterias (muestra simulada)")
plt.xlabel("Horas de vida util")
plt.ylabel("Frecuencia")
plt.axvline(media, color='red', linestyle='--', label=f'Media muestral ({media_muestral:.2f})')
plt.legend()
plt.show()