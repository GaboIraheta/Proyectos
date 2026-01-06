import getUserRoles from "/src/utils/getUserRoles.js";

const routes = {
  "/": { html: "src/pages/Home/home.html", init: null },
  "/productos": { html: "src/pages/Products/products.html", init: "/src/Scripts/products.js" },
  "/carrito": { html: "src/pages/Cart/cart.html", init: "/src/Scripts/cart.js" },
  "/login": { html: "src/pages/Login/login.html", init: "/src/Scripts/login.js" },
  "/register": { html: "src/pages/Register/register.html", init: "/src/Scripts/register.js" },
  "/empleados-productos": { html: "src/pages/EmployeeProducts/EProducts.html", init: "/src/Scripts/empleados-productos.js" },
  "/administrar-empleados": { html: "src/pages/AdminView/adminView.html", init: "/src/Scripts/adminView.js" },
};

const protectedRoutes = {
  "/empleados-productos": { addmitedRoles: ["employee", "admin"] },
  "/administrar-empleados": { addmitedRoles: ["admin"] },
};

// Renderizado de vistas
async function render() {
  const path = window.location.pathname;
  const route = routes[path] || { html: "src/pages/NotFound/notFound.html", init: null };
  const app = document.getElementById("app");
  let role = "guest";

  const response = await getUserRoles();
  if (!response.error) {
    role = response;

    if(role !== 'guest' && !document.getElementById('user-label')) {
      const navbar = document.getElementById('navbar');
      const usernameLabel = document.createElement('p')
      usernameLabel.id = 'user-label';
      const username = localStorage.getItem('username');
      usernameLabel.innerText = `Bienvenido ${username}`;

      navbar.appendChild(usernameLabel);
    }
    // ensure logout link works when user is authenticated
    if (role !== 'guest') {
      const logoutBtn = document.getElementById('logout-button');
      if (logoutBtn && !logoutBtn.dataset.attached) {
        logoutBtn.addEventListener('click', async (e) => {
          e.preventDefault();
          try {
            await fetch(`${import.meta.env.VITE_API_URL}/api/users/logout`, {
              method: 'POST',
              credentials: 'include'
            });
          } catch (err) {
            console.error('Error during logout request:', err);
          }
          // Clear client-side session data and redirect
          localStorage.removeItem('username');
          const userLabel = document.getElementById('user-label');
          if (userLabel) userLabel.remove();
          // navigate to home
          history.pushState(null, null, '/');
          render();
        });
        logoutBtn.dataset.attached = 'true';
      }
    }
  }

  showLinksBasedOnRole(role);

  if (protectedRoutes[path]) {
    try {

      if (!protectedRoutes[path].addmitedRoles.includes(role.toLowerCase())) {
        window.location.href = "/";
        return;
      }

    } catch (error) {
      window.location.href = "/";
    }
  }

  try {
    const res = await fetch(route.html);
    const html = await res.text();
    app.innerHTML = html;

    if (route.init) {
      const module = await import(route.init);
      module.init && module.init(role);
    }
  } catch (err) {
    app.textContent = '';
    const p = document.createElement('p');
    p.textContent = `Error cargando la vista: ${err.message}`;
    app.appendChild(p);
  }
}

// Navegación sin recargar
function navigateTo(url) {
  history.pushState(null, null, url);
  render();
}

// Interceptar enlaces
document.addEventListener("click", (e) => {
  if (e.target.matches("[data-link]")) {
    e.preventDefault();
    navigateTo(e.target.getAttribute("href"));
  }
});

function showLinksBasedOnRole(role) {
  const navbar = document.getElementById("navbar");
  navbar.querySelectorAll(`.${role.toLowerCase()}-link`).forEach(link => {
    link.classList.remove("hidden");
  });
}

window.addEventListener("popstate", render);
render();
