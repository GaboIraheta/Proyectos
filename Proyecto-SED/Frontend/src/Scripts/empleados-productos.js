import createProduct from "/src/utils/createProduct.js";

export function init() {

  const container = document.querySelector(".employee-products");
  if (!container) return;

  let products = [];

  // Convierte File a base64 Data URL
  const fileToBase64 = (file) => {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.onload = () => resolve(reader.result);
      reader.onerror = reject;
      reader.readAsDataURL(file);
    });
  };
  

  async function getProducts() {
    try {
      const response = await fetch(`${import.meta.env.VITE_API_URL}/api/products/get`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json'
        }
      });

      /*
      result = {
        "products": [
          {
            "_id": "691d773705f10674186802bb",
            "name": "peras",
            "description": "son varias",
            "category": "frutas",
            "price": 13434.05,
            "stock": 20,
            "active": false,
            "image": {
              "pathBBDD": "Uploads/peras_1763538825447.png",
              "file": "iVBORw0KGgoAAAANSUhEUgAAAMgAAADICAAAAAB3EMkAAAAA"
            }
          }
        ]
      }
      */

      if(response.ok){
        const data = await response.json();
        return data.products || [];
      } else {
        return [];
      }
    } catch (error) {
      alert('Error de red al cargar los productos');
      return [];
    }
  }

  async function updateProducts(product, enable) { // enable es false si se actualiza, true si es enable
    try {

      let route;
      let body;

      if (enable) {
        
        route = `/api/products/enable/${product._id}`;
        body = {
          active : !product.active
        };

      } else {

        // Si la imagen es un File, convertir a base64 y enviar JSON
        if (product.image.file instanceof File) {
          const base64 = await fileToBase64(product.image.file);
          // mutamos product.image para que el caller vea la imagen como base64
          product.image.file = base64;
          product.image.pathBBDD = null;
        }

        route = `/api/products/update/${product._id}`;
        delete product._id;
        body = {
          updatedData : product
        };

      }

      // Enviar JSON (imagen ya convertida a base64 si era File)
      const response = await fetch(`${import.meta.env.VITE_API_URL}${route}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        credentials: 'include',
        body: JSON.stringify(body),
      });

      const result = await response.json();

      // result = { message } siempre, tanto para enable como para update product

      if (!response.ok) {

        alert(result.error);
        return false;
      }
      return true;
    } catch (error) {
      console.error('Error de red al actualizar el producto', error);
      alert('Error de red al actualizar el producto');
      return false;
    }
  }

  async function deleteProducts(id) {
    try {
      const response = await fetch(`${import.meta.env.VITE_API_URL}/api/products/delete/${id}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json'
        },
        credentials: 'include'
      });

      const result = await response.json();

      // result = { message }

      if(!response.ok){
        alert('Error al eliminar el producto, intente más tarde');
        return;
      }
    } catch (error) {
      alert('Error de red al eliminar el producto');
      return;
    }
  }

  async function addProducts(product){
    try {
      // Si la imagen es File, convertir a base64 y enviarla en JSON
      if (product.image instanceof File) {
        const base64 = await fileToBase64(product.image);
        product.image = base64;
      }

      // Enviar JSON (imagen ya convertida a base64 si era File)
      const response = await fetch(`${import.meta.env.VITE_API_URL}/api/products/add`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include',
        body: JSON.stringify(product),
      });

      const result = await response.json();

      /*
      {
        "products": [
          {
            "_id": "691fdb2abe585f9b6d2b9a41",
            "name": "fresas",
            "description": "son varias",
            "category": "frutas",
            "price": 12.05,
            "stock": 10,
            "active": true,
            "image": "Uploads/fresas_1763695402963.png"
          },
        ],
        "message": "Producto agregado exitosamente."
      }
      */

      if(!response.ok){
        alert('Error al agregar el producto, intente más tarde');
        return false;
      }
      return true;
    } catch (error) {
      console.error('Error de red al agregar el producto', error);
      alert('Error de red al agregar el producto');
      return false;
    }
  }

  // --- Renderizado principal ---
  const renderProducts = async () => {
    const list = document.querySelector(".products-list");
    list.textContent = "";

    products = await getProducts();

    // Procesar productos con createProduct para normalizar las imágenes
    const processedProducts = await Promise.all(
      products.map(async (product) => {
        try {
          const processed = await createProduct(product);
          return processed || product;
        } catch (error) {
          console.error('Error procesando producto:', error);
          return product;
        }
      })
    );

    products = processedProducts;

    products.forEach((product) => {
      const card = document.createElement("div");
      card.className = "product-card";
      if (!product.active) card.classList.add("inactive");

      const img = document.createElement('img');
      img.alt = product.name || '';
      if (typeof product.image.file === 'string' && product.image.file) img.src = product.image.file;

      const title = document.createElement('h3');
      title.textContent = product.name || '';

      const priceP = document.createElement('p');
      priceP.textContent = `Precio: $${(product.price || 0).toFixed(2)}`;

      const actions = document.createElement('div');
      actions.className = 'actions';

      const editBtn = document.createElement('button');
      editBtn.className = 'edit-btn';
      editBtn.textContent = '✏️ Editar';
      // pasar solo el id al editor (editProduct busca por id)
      editBtn.addEventListener('click', () => editProduct(product._id));

      const softBtn = document.createElement('button');
      softBtn.className = 'soft-delete-btn';
      softBtn.textContent = product.active ? '🟠 Desactivar' : '🟢 Activar';
      softBtn.addEventListener('click', async () => {
        try {
          const confirmation = await updateProducts(product, true);
          if (!confirmation) return;
          await renderProducts();
        } catch (error) {
          console.error('Error en soft delete:', error);
        }
      });

      const hardBtn = document.createElement('button');
      hardBtn.className = 'hard-delete-btn';
      hardBtn.textContent = '🗑️ Eliminar';
      hardBtn.addEventListener('click', async () => {
        if (confirm(`¿Eliminar definitivamente ${product.name}?`)) {
          try {
            await deleteProducts(product._id);
            products = products.filter((p) => p._id !== product._id);
            await renderProducts();
          } catch (error) {
            console.error('Error en hard delete:', error);
          }
        }
      });

      actions.appendChild(editBtn);
      actions.appendChild(softBtn);
      actions.appendChild(hardBtn);

      card.appendChild(img);
      card.appendChild(title);
      card.appendChild(priceP);
      card.appendChild(actions);

      list.appendChild(card);
    });
  };

  // --- Mostrar formulario para agregar producto ---
  const showAddForm = () => {
    const existingForm = document.querySelector(".product-form");
    if (existingForm) existingForm.remove();

    const form = document.createElement("div");
    form.className = "product-form";

    const h3 = document.createElement('h3');
    h3.textContent = 'Agregar nuevo producto';

    const inputName = document.createElement('input');
    inputName.type = 'text';
    inputName.id = 'new-name';
    inputName.placeholder = 'Nombre del producto';

    const inputPrice = document.createElement('input');
    inputPrice.type = 'number';
    inputPrice.id = 'new-price';
    inputPrice.placeholder = 'Precio';
    inputPrice.min = '0';
    inputPrice.step = '0.01';

    const inputImage = document.createElement('input');
    inputImage.type = 'file';
    inputImage.id = 'new-image';
    inputImage.accept = 'image/*';

    const actionsDiv = document.createElement('div');
    actionsDiv.className = 'form-actions';

    const saveBtn = document.createElement('button');
    saveBtn.id = 'save-product';
    saveBtn.type = 'button';
    saveBtn.textContent = 'Guardar';

    const cancelBtn = document.createElement('button');
    cancelBtn.id = 'cancel-product';
    cancelBtn.type = 'button';
    cancelBtn.textContent = 'Cancelar';

    actionsDiv.appendChild(saveBtn);
    actionsDiv.appendChild(cancelBtn);

    form.appendChild(h3);
    form.appendChild(inputName);
    form.appendChild(inputPrice);
    form.appendChild(inputImage);
    form.appendChild(actionsDiv);

    container.appendChild(form);

    // Guardar producto
    saveBtn.addEventListener("click", async () => {
      const name = inputName.value.trim();
      const price = parseFloat(inputPrice.value);
      const imageFile = inputImage.files[0];

      if (!name || isNaN(price) || !imageFile) {
        alert("Por favor completa todos los campos incluida la imagen");
        return;
      }

      try {
        const newProduct = {
          name,
          price,
          active: true,
          image: imageFile,
        };

        await addProducts(newProduct);
        products.push(newProduct);
        form.remove();
        await renderProducts();
      } catch (error) {
        console.error('Error al agregar producto:', error);
        alert('Error al agregar el producto');
      }
    });

    // Cancelar
    form.querySelector("#cancel-product").addEventListener("click", () => {
      form.remove();
    });
  };

  // --- Editar producto existente ---
  const editProduct = (id) => {
    const product = products.find((p) => p._id === id);
    if (!product) return;

    const existingForm = document.querySelector(".product-form");
    if (existingForm) existingForm.remove();

    const form = document.createElement("div");
    form.className = "product-form";

    const h3 = document.createElement('h3');
    h3.textContent = 'Editar producto';

    const inputName = document.createElement('input');
    inputName.type = 'text';
    inputName.id = 'edit-name';
    inputName.value = product.name || '';

    const inputPrice = document.createElement('input');
    inputPrice.type = 'number';
    inputPrice.id = 'edit-price';
    inputPrice.value = product.price || 0;
    inputPrice.min = '0';
    inputPrice.step = '0.01';

    const inputImage = document.createElement('input');
    inputImage.type = 'file';
    inputImage.id = 'edit-image';
    inputImage.accept = 'image/*';

    const note = document.createElement('small');
    note.textContent = 'Deja en blanco si no deseas cambiar la imagen';

    const actionsDiv = document.createElement('div');
    actionsDiv.className = 'form-actions';

    const saveBtn = document.createElement('button');
    saveBtn.id = 'save-edit';
    saveBtn.type = 'button';
    saveBtn.textContent = 'Guardar cambios';

    const cancelBtn = document.createElement('button');
    cancelBtn.id = 'cancel-edit';
    cancelBtn.type = 'button';
    cancelBtn.textContent = 'Cancelar';

    const errorMessage = document.createElement('p');
    errorMessage.id = 'error-message';
    errorMessage.textContent = '';

    actionsDiv.appendChild(saveBtn);
    actionsDiv.appendChild(cancelBtn);

    form.appendChild(h3);
    form.appendChild(inputName);
    form.appendChild(inputPrice);
    form.appendChild(inputImage);
    form.appendChild(note);
    form.appendChild(errorMessage); 
    form.appendChild(actionsDiv);

    container.appendChild(form);

    // Guardar cambios
    saveBtn.addEventListener('click', async () => {
      const name = inputName.value.trim();
      const imageFile = inputImage.files[0];
      const price = parseFloat(inputPrice.value);

      if (isNaN(price)) {
        errorMessage.textContent = 'Ingrese un precio válido';
        return;
      }

      if (!name || isNaN(price)) {
        errorMessage.textContent = 'Por favor completa los campos requeridos';
        return;
      }

      if (typeof name !== 'string') {
        errorMessage.textContent = 'Entradas inválidas';
        return;
      }

      try {
        product.name = name;
        product.price = price;

        // Si hay una nueva imagen, asignar File (handler will convert to base64)
        if (typeof imageFile !== 'undefined') {
          product.image.file = imageFile;
        }

        await updateProducts(product, false);
        form.remove();
        await renderProducts();
      } catch (error) {
        console.error('Error al editar producto:', error);
        alert('Error al editar el producto');
      }
    });

    // Cancelar
    cancelBtn.addEventListener('click', () => {
      form.remove();
    });
  };

  // --- Listeners generales ---
  document.querySelector(".add-product-btn").addEventListener("click", showAddForm);

  renderProducts();
}
