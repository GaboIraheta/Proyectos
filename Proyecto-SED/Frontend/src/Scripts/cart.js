import createProduct from "/src/utils/createProduct.js";

export function init(role) {

    if (role.toLowerCase() !== 'user') {
        alert('Solo los usuarios normales pueden acceder al carrito');
        window.location.href = '/';
    }

    const cartItemsContainer = document.getElementById("cartItems");
    const totalElement = document.getElementById("cartTotal");
    const checkoutBtn = document.getElementById("checkoutBtn");
    const errorMessage = document.getElementById("error-message");

    let cart = [];

    async function getCart() {
        try {
            const response = await fetch(`${import.meta.env.VITE_API_URL}/api/users/cart`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include',
            });

            // remove unused intermediate call to response.json()

            // accedes a la lista de carrito del usuario con result.cart
            /*
            result = {
                "cart": [
                    {
                        "name": "manzanas",
                        "description": "son varias",
                        "category": "frutas",
                        "price": 12.05,
                        "stock": 10,
                        "active": true,
                        "image": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMgAAADICAAAAAB3EMkAAAAA"
                    }
                ]
            }
            */

            if (response.ok) {
                const data = await response.json();
                return data.cart || [];
            } else {
                const data = await response.json().catch(() => ({}));
                alert(data.error || "Error al cargar el carrito.");
                return [];
            }
        } catch (error) {
            errorMessage.textContent = "Error de red al cargar el carrito.";
            return [];
        }
    }

    async function deleteFromCart(identifier) {
        try {
            // Enviar tanto id como name para mayor compatibilidad con el backend
            const response = await fetch(`${import.meta.env.VITE_API_URL}/api/users/deleteFromCart`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include',
                body: JSON.stringify({'name':identifier})
            });

            const result = await response.json().catch(() => ({}));

            // result = { message }

            if (!response.ok) {
                alert(result.error || "Error al eliminar el producto del carrito.");
                window.location.reload();
            }
        } catch (error) {
            alert("Error de red al eliminar el producto del carrito.");
        }
    }


    async function renderCart() {
        cartItemsContainer.textContent = "";
        errorMessage.textContent = "";

        cart = await getCart();
        const processedProducts = await Promise.all(
            cart.map(async (product) => {
                try {
                    const processed = await createProduct(product);
                    return processed || product;
                } catch (error) {
                    console.error('Error procesando producto:', error);
                    return product;
                }
            })
        );
        cart = processedProducts;

        if (!cart || cart.length === 0) {
            const p = document.createElement('p');
            p.textContent = 'Tu carrito está vacío 🛍️';
            cartItemsContainer.appendChild(p);
            totalElement.textContent = '0.00';
            return;
        }
        let total = 0;

        cart.forEach((product, index) => {
            const item = document.createElement('div');
            item.classList.add('cart-item');

            const img = document.createElement('img');
            img.alt = product.name || '';
            if (typeof product.image.file === 'string' && product.image.file) img.src = product.image.file;

            const details = document.createElement('div');
            details.className = 'cart-item-details';

            const h3 = document.createElement('h3');
            h3.textContent = product.name || '';

            const p = document.createElement('p');
            p.textContent = `Precio: $${(product.price || 0).toFixed(2)}`;

            details.appendChild(h3);
            details.appendChild(p);

            const btn = document.createElement('button');
            btn.className = 'remove-btn';
            btn.dataset.index = product.name;
            btn.textContent = 'Quitar';

            item.appendChild(img);
            item.appendChild(details);
            item.appendChild(btn);

            cartItemsContainer.appendChild(item);
            total += Number(product.price || 0);
        });

        totalElement.textContent = total.toFixed(2);
    }

    // Eliminar producto
    cartItemsContainer.addEventListener("click", async (e) => {
        if (e.target.classList.contains("remove-btn")) {

            const index = e.target.dataset.index;
            await deleteFromCart(index);

            cart.splice(index, 1);
            renderCart();
        }
    });

    // Finalizar compra (ejemplo)
    checkoutBtn.addEventListener("click", async () => {
        if (cart.length === 0) {
            alert("Tu carrito está vacío.");
            return;
        }
        try {
            for (const item of cart) {
                const identifier = item.id || item.name;
                revokeIfObjectURL(item.image);
                await deleteFromCart(identifier);
            }
        } catch (error) {
            console.error("Error al finalizar la compra:", error);
            alert('Error al finalizar la compra. Intenta de nuevo.');
            return;
        }
        alert("Compra finalizada con éxito");
        cart.length = 0; // Vaciar carrito
        await renderCart();
    });

    function revokeIfObjectURL(url) {
        try {
            if (typeof url === 'string' && url.startsWith('blob:')) {
                URL.revokeObjectURL(url);
            }
        } catch (e) {
            // ignore
        }
    }

    renderCart();

}