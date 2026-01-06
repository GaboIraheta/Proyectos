import containsMongoOperators from "/src/utils/containsMongoOperators.js";

export function init() {

  const container = document.querySelector(".admin-employees");
  if (!container) return;

  let employees = [];

  async function getEmployees() {
    try {
      const response = await fetch(`${import.meta.env.VITE_API_URL}/api/employees/get`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json'
        },
        credentials: 'include',
      });

      /*
      result = {
        "employees": [
          {
            "_id": "691d75a2c98d2dd6177dffe8",
            "name": "Esmeralda Villanueva",
            "dui": "06778667-5",
            "email": "gabo7iraheta01@gmail.com",
            "role": true,
            "active": true
          }
        ]
      }
      */

      if (!response.ok) {
        const result = await response.json();
        alert(result.error || 'Error desconocido');
        return [];
      }

      const data = await response.json();
      return data.employees || []; //corecctisimo, asi se devuelve

    } catch (error) {
      alert('Error de red al obtener los empleados');
      return [];
    }
  }

  async function updateEmployee(employee, isCredentials) {

    // te actualice toda esta parte hasta el result = response.json()
    // con este codigo manejas actualizar credenciales y activar/desactivar la cuenta del empleado
    // tenes que mandar true en el segundo parametro en la llamada cuando actualices las credenciales, false para enable

    try {

      let route;
      let body;

      if (isCredentials) {

        route = `/api/employees/update/${employee._id}`;
        body = {
          credentials: {
            email: employee.email,
            password: employee.password
          }
        };
        header = true;
      } else {

        route = `/api/employees/enable/${employee._id}`;
        body = {
          active: !employee.active
        };
      }

      const response = await fetch(`${import.meta.env.VITE_API_URL}${route}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json', 'update': true, },
        credentials: 'include',
        body: JSON.stringify(body)
      });

      const result = await response.json();

      // result = { message } para ambos casos de update o enable

      if (!response.ok) {
        alert(result.error || 'Error desconocido');
        return false;
      }

      return true;
    } catch (error) {
      alert('Error de red al actualizar el usuario');
      return false;
    }
  }

  async function deleteEmployee(id) {
    try {
      const response = await fetch(`${import.meta.env.VITE_API_URL}/api/employees/delete/${id}`, {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include'
      });

      const result = await response.json();

      // result = { "message"  : "Empleado eliminado exitosamente" }

      if (!response.ok) {
        alert(result.error || 'Error desconocido');
        return false;
      }

      return true;
    } catch (error) {
      alert('Error de red al eliminar el usuario');
      return false;
    }
  }

  async function addEmployee(employee) {

    // tenes que mandar en el body un objeto asi
    /*
    employee = {
      "name" : "Esmeralda Villanueva",
      "email" : "gabo7iraheta03@gmail.com",
      "password" : "Afb092ebbf$12345",
      "role" : false, (false si es empleado nomal y true si es admin)
      "active" : true
    } o sea employee deberia ser un objeto de esta forma
    */

    employee.active = true;

    try {
      const response = await fetch(`${import.meta.env.VITE_API_URL}/api/employees/register`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify(employee),
      });

      const result = await response.json();

      /*
      result = {
        "message": "Empleado registrado exitosamente.",
        "employee": {
          "_id": "691f7bd7dbd25dbd0ec5a59c",
          "name": "Esmeralda Villanueva",
          "email": "gabo7iraheta03@gmail.com",
          "role": false,
          "active": true
        }
      } si registras un admin solo mandas role : true, empleado normal role : false
      */

      if (!response.ok) {
        alert('Error al agregar el empleado, intente más tarde');
        return false;
      }
      return true;
    } catch (error) {
      alert('Error de red al agregar el empleado');
      return false;
    }
  }

  // --- Render principal ---
  const renderEmployees = async () => {
    const list = document.querySelector(".employees-list");
    list.textContent = "";
    employees = await getEmployees();

    employees.forEach((emp) => {
      const card = document.createElement("div");
      card.className = "employee-card";
      if (!emp.active) card.classList.add("inactive");

      const h3 = document.createElement('h3');
      h3.textContent = emp.name || '';

      const p = document.createElement('p');
      const b = document.createElement('b');
      b.textContent = 'Correo:';
      p.appendChild(b);
      p.appendChild(document.createTextNode(' ' + (emp.email || '')));

      const actions = document.createElement('div');
      actions.className = 'actions';

      const editBtn = document.createElement('button');
      editBtn.className = 'edit-btn';
      editBtn.textContent = '✏️ Editar';
      editBtn.addEventListener('click', () => editEmployee(emp._id));

      const softBtn = document.createElement('button');
      softBtn.className = 'soft-delete-btn';
      softBtn.textContent = emp.active ? '🟠 Desactivar' : '🟢 Activar';

      softBtn.addEventListener('click', async () => {
        try {
          const success = await updateEmployee(emp, false);
          emp.active = !emp.active;
          if (success) await renderEmployees();
        } catch (error) {
          console.error('Error en soft delete:', error);
        }
      });

      const delBtn = document.createElement('button');
      delBtn.className = 'delete-btn';
      delBtn.textContent = '❌ Eliminar empleado';
      delBtn.addEventListener('click', async () => {
        if (confirm(`¿Seguro que deseas eliminar a ${emp.name}? Esta acción es irreversible.`)) {
          try {
            const success = await deleteEmployee(emp._id);
            if (success) {
              employees = employees.filter((e) => e.id !== emp.id);
              await renderEmployees();
            }
          } catch (error) {
            console.error('Error en delete:', error);
          }
        }
      });

      actions.appendChild(editBtn);
      actions.appendChild(softBtn);
      actions.appendChild(delBtn);

      card.appendChild(h3);
      card.appendChild(p);
      card.appendChild(actions);

      list.appendChild(card);
    });
  };

  // --- Mostrar formulario para agregar empleado ---
  const showAddForm = () => {
    const existingForm = document.querySelector(".employee-form");
    if (existingForm) existingForm.remove();

    const form = document.createElement("div");
    form.className = "employee-form";

    const h3 = document.createElement('h3');
    h3.textContent = 'Agregar nuevo empleado';

    const inputName = document.createElement('input');
    inputName.type = 'text';
    inputName.id = 'new-name';
    inputName.placeholder = 'Nombre completo';

    const inputEmail = document.createElement('input');
    inputEmail.type = 'email';
    inputEmail.id = 'new-email';
    inputEmail.placeholder = 'Correo electrónico';

    const inputPassword = document.createElement('input');
    inputPassword.type = 'password';
    inputPassword.id = 'new-password';
    inputPassword.placeholder = 'Contraseña';

    const inputConfirmPassword = document.createElement('input');
    inputConfirmPassword.type = 'password';
    inputConfirmPassword.id = 'confirm-password';
    inputConfirmPassword.placeholder = 'Confirmar contraseña';

    const actionsDiv = document.createElement('div');
    actionsDiv.className = 'form-actions';

    const saveBtn = document.createElement('button');
    saveBtn.id = 'save-employee';
    saveBtn.type = 'button';
    saveBtn.textContent = 'Guardar';

    const cancelBtn = document.createElement('button');
    cancelBtn.id = 'cancel-employee';
    cancelBtn.type = 'button';
    cancelBtn.textContent = 'Cancelar';

    const adminCheckboxWrapper = document.createElement('div');
    adminCheckboxWrapper.className = 'admin-checkbox';

    const inputIsAdmin = document.createElement('input');
    inputIsAdmin.id = 'isAdmin';
    inputIsAdmin.type = 'checkbox';
    inputIsAdmin.name = 'Administrador';

    const labelIsAdmin = document.createElement('label');
    labelIsAdmin.htmlFor = 'isAdmin';
    labelIsAdmin.textContent = 'Administrador';

    adminCheckboxWrapper.appendChild(inputIsAdmin);
    adminCheckboxWrapper.appendChild(labelIsAdmin);

    const errorText = document.createElement('h3');
    errorText.id = 'error-text';
    errorText.textContent = '';

    actionsDiv.appendChild(saveBtn);
    actionsDiv.appendChild(cancelBtn);

    form.appendChild(h3);
    form.appendChild(inputName);
    form.appendChild(inputEmail);
    form.appendChild(inputPassword);
    form.appendChild(inputConfirmPassword);
    form.appendChild(adminCheckboxWrapper);
    form.appendChild(actionsDiv);
    form.appendChild(errorText);

    container.appendChild(form);

    saveBtn.addEventListener('click', async () => {
      const name = inputName.value.trim();
      const email = inputEmail.value.trim();
      const password = inputPassword.value.trim();
      const confirmation = inputConfirmPassword.value.trim();
      const role = inputIsAdmin.checked;

      if (!email || !password || !confirmation) {
        errorText.textContent = 'Por favor complete todos los campos';
        return;
      }

      if (password !== confirmation) {
        errorText.textContent = 'Las contraseñas deben de coincidir';
        return;
      }

      const regexp = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!regexp.test(email)) {
        errorText.textContent = 'Por favor, ingresa un correo electrónico válido.';
        return false;
      }

      if (password.length < 6) {
        errorText.textContent = 'La contraseña debe tener al menos 6 caracteres.';
        return false;
      }

      if (password.length > 50) {
        errorText.textContent = 'La contraseña no debe exceder los 50 caracteres.';
        return false;
      }

      if (password.includes(' ')) {
        errorText.textContent = 'La contraseña no debe contener espacios.';
        return false;
      }

      

      if (containsMongoOperators(name) || containsMongoOperators(email) || containsMongoOperators(password) || containsMongoOperators(confirmation)) {
        errorText.textContent = 'Entradas inválidas';
        return;
      }

      const newEmp = { name, email, password, role };

      try {
        const success = await addEmployee(newEmp);
        if (success) {
          form.remove();
          await renderEmployees();
        }
      } catch (error) {
        console.error('Error al agregar empleado:', error);
      }
    });

    cancelBtn.addEventListener('click', () => {
      form.remove();
    });
  };

  // --- Editar empleado ---
  const editEmployee = (id) => {
    const emp = employees.find((e) => e._id === id);
    if (!emp) return;

    const existingForm = document.querySelector(".employee-form");
    if (existingForm) existingForm.remove();

    const form = document.createElement("div");
    form.className = "employee-form";

    const h3 = document.createElement('h3');
    h3.textContent = 'Editar empleado';


    const inputEmail = document.createElement('input');
    inputEmail.type = 'email';
    inputEmail.id = 'edit-email';
    inputEmail.value = emp.email || '';

    const inputPassword = document.createElement('input');
    inputPassword.type = 'password';
    inputPassword.id = 'edit-password';
    inputPassword.placeholder = 'Nueva contraseña (dejar vacío para no cambiar)';

    const inputConfirmPassword = document.createElement('input');
    inputConfirmPassword.type = 'password';
    inputConfirmPassword.id = 'confirm-edit-password';
    inputConfirmPassword.placeholder = 'En caso de cambiar contraseña confirmar aquí';

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

    const errorText = document.createElement('h4');
    errorText.id = 'error-text';
    errorText.textContent = '';

    actionsDiv.appendChild(saveBtn);
    actionsDiv.appendChild(cancelBtn);

    form.appendChild(h3);
    form.appendChild(inputEmail);
    form.appendChild(inputPassword);
    form.appendChild(inputConfirmPassword);
    form.appendChild(errorText);
    form.appendChild(actionsDiv);

    container.appendChild(form);

    saveBtn.addEventListener('click', async () => {
      const email = inputEmail.value.trim();
      const password = inputPassword.value.trim();
      const confirmPassword = inputConfirmPassword.value.trim();
      
      if (!email) {
        errorText.textContent = 'Por favor completa los campos requeridos';
        return;
      }

      if (containsMongoOperators(email) || containsMongoOperators(password)) {
        errorText.textContent = 'Entradas inválidas';
        return;
      }

      if (typeof email !== 'string' || typeof password !== 'string' || typeof confirmPassword !== 'string') {
        errorText.textContent = 'Entrada inválida.';
        return false;
      }
      const regexp = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!regexp.test(email)) {
        errorText.textContent = 'Por favor, ingresa un correo electrónico válido.';
        return false;
      }
      if (password && password.length < 6) {
        errorText.textContent = 'La contraseña debe tener al menos 6 caracteres.';
        return false;
      }
      if (password && password.length > 50) {
        errorText.textContent = 'La contraseña no debe exceder los 50 caracteres.';
        return false;
      }
      if (password && password.includes(' ')) {
        errorText.textContent = 'La contraseña no debe contener espacios.';
        return false;
      }
      const regexPassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}$/;
      if (password && !regexPassword.test(password)) {
        errorText.textContent = 'La contraseña debe contener al menos una letra mayúscula, una letra minúscula, un número y un carácter especial.';
        return false;
      }
      if (password && password !== confirmPassword) {
        errorText.textContent = 'Las contraseñas no coinciden.';
        return false;
      }

      emp.email = email;
      if (password) emp.password = password;

      try {
        const success = await updateEmployee(emp, true);
        if (success) {
          form.remove();
          await renderEmployees();
        }
      } catch (error) {
        console.error('Error al editar empleado:', error);
      }
    });

    cancelBtn.addEventListener('click', () => {
      form.remove();
    });
  };

  // --- Eventos globales ---
  document.querySelector(".add-employee-btn").addEventListener("click", showAddForm);

  renderEmployees();
}
