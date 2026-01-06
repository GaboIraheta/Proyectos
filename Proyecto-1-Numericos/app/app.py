import gradio as gr
from logistic_regression_model.logistic_regression import classify_flowers
from mult_regression_model.mult_regression import predict_prices 
from notebooks.notebooks import show_notebook   

def create_interface():
    
    with gr.Blocks() as app:
        
        gr.Markdown("## 🧠 Aplicación de Modelos Predictivos")

        with gr.Tabs():
            
            with gr.Tab("Regresión Logística"):
                
                gr.Markdown("### 🌼 Clasificación de Flores")
                
                image_input = gr.Image(label="Sube una imagen de flor")
                predict_btn = gr.Button("Clasificar")
                result_text = gr.Textbox(label="Resultado de clasificación", lines=1)
                
                predict_btn.click(classify_flowers, inputs=image_input, outputs=result_text)
                
                gr.Markdown("### 📓 Notebook de entrenamiento")
                
                btn_show_notebook = gr.Button("Ver notebook de entrenamiento")
                output_nootebook = gr.HTML()
                
                btn_show_notebook.click(fn=lambda: show_notebook("Flowers_Recognition.ipynb"),
                                         outputs=output_nootebook)


            with gr.Tab("Regresión Multivariada"):
                
                gr.Markdown("### 💵 Predicción de Precios")
                
                csv_input = gr.File(file_types=[".csv"], label="Sube archivo CSV")
                predict_csv_btn = gr.Button("Predecir Precios")
                result_table = gr.DataFrame(label="Tabla de precios predichos")
                
                predict_csv_btn.click(predict_prices, inputs=csv_input, outputs=result_table)
                
                gr.Markdown("### 📓 Notebook de entrenamiento")
                
                btn_show_notebook = gr.Button("Ver notebook de entrenamiento")
                output_nootebook = gr.HTML()
                
                btn_show_notebook.click(fn=lambda: show_notebook("RegresionMultivariable.ipynb"),
                                         outputs=output_nootebook)

    return app

app = create_interface()
app.launch()