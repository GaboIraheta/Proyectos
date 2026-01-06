import createProduct from "/src/utils/createProduct.js";

export function init(role) {
  const productsList = document.getElementById("productsList");
  const searchInput = document.getElementById("searchInput");

  // todo aqui repetiste esto, esta en empleados productos tambien, ahi ve en donde lo dejas
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

      if (response.ok) {
        const data = await response.json();
        return data.products || [];
      } else {
        alert('Error al cargar los productos');
        return [];
      }
    } catch (error) {
      alert('Error de red al cargar los productos');
      return [];
    }
  }

  async function addToCart(product) {
    try {

      /*
      body = { en este product omitis el id, solo le envias estos campos tal cual como se ven aqui xddd
        "product" : {
          "name" : "manzanas",
          "description" : "son varias",
          "category" : "frutas",
          "price" : 12.05,
          "stock" : 10,
          "active" : true,
          "image" : {
            "pathBBDD" : "Uploads/fresas.png",
            "file" : "hwgfiugwdufhvcwiduvfku"
          }
        }
      }
      */
      delete product._id;

      const response = await fetch(`${import.meta.env.VITE_API_URL}/api/users/addCart`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        credentials: 'include',
        body: JSON.stringify({ product })
      });

      const data = await response.json();

      // result = { message }

      if (!response.ok) {
        alert("Error al agregar el producto al carrito.");
      }
    } catch (error) {
      alert("Error de red al agregar el producto al carrito.");
    }
  }

  let products = [];

  // Renderizar productos
  async function renderProducts(list = null) {
    productsList.textContent = "";
    products = await getProducts();
    // Cargar productos desde backend si aún no están cargados

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
    products = processedProducts


    if (!products || products.length === 0) {
      const msg = document.createElement('p');
      msg.textContent = 'No se encontraron productos 🔍';
      productsList.appendChild(msg);
      return;
    }

    products.forEach((p) => {
      const card = document.createElement("div");
      card.classList.add("product-card");

      const img = document.createElement('img');
      img.alt = p.name || '';
      if (typeof p.image.file === 'string' && p.image.file) img.src = p.image.file;

      const content = document.createElement('div');
      content.className = 'product-card-content';

      const h3 = document.createElement('h3');
      h3.textContent = p.name || '';

      const priceP = document.createElement('p');
      const strong = document.createElement('strong');
      strong.textContent = `$${((p.price || 0)).toFixed(2)}`;
      priceP.appendChild(strong);

      const btn = document.createElement('button');
      btn.className = 'add-to-cart';
      btn.dataset.id =  p._id;
      btn.textContent = 'Agregar al carrito';

      content.appendChild(h3);
      content.appendChild(priceP);
      content.appendChild(btn);

      card.appendChild(img);
      card.appendChild(content);

      productsList.appendChild(card);
    });
  }

  // Evento para agregar al carrito
  productsList.addEventListener("click", async (e) => {
    if (e.target.classList.contains("add-to-cart")) {
      if (role.toLowerCase() !== "user") {
        alert('Solo los usuarios normales pueden acceder al carrito');
        window.location.href = "/login";
        return;
      }
      // dataset id may be a string (Mongo _id) — compare as strings
      const id = e.target.dataset.id;
      const product = products.find((p) => p._id === id);
      if (product) {
        await addToCart(product);
        alert(`${product.name} agregado al carrito 🛒`);
      }
    }
  });

  // Buscador dinámico
  searchInput.addEventListener("input", async (e) => {
    const term = e.target.value.toLowerCase();
    const filtered = products.filter((p) => p.name.toLowerCase().includes(term));
    renderProducts(filtered);
  });

  renderProducts(products);
}
