import nbformat
from nbconvert import HTMLExporter
import os

def show_notebook(filename) :
    
    base_dir = os.path.dirname(__file__)
    path = os.path.join(base_dir, filename)
    
    try :
        
        with open(path, "r", encoding="utf-8") as file :
            nb = nbformat.read(file, as_version=4)
            
        exporter = HTMLExporter()
        
        exporter.exclude_input_prompt = True
        exporter.exclude_input_prompt = True
        exporter.exclude_output_prompt = True
        
        body, _ = exporter.from_notebook_node(nb)
        
        return inject_style(body)
    
    except Exception :
        return f"<p> Error: {str(Exception)}</p>"
    
def inject_style(html_content):
    style = """
    <style>
        body {
            background-color: #f4f4f4;
            color: #000;
        }
        pre, code {
            background-color: #1e1e1e;
            color: #dcdcdc;
            padding: 10px;
            border-radius: 5px;
            overflow-x: auto;
        }
        .output_area {
            background: #f8f8f8;
            padding: 8px;
            margin: 4px 0;
        }
    </style>
    """
    return style + html_content