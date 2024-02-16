document.addEventListener('DOMContentLoaded', () => {
    const usuario = JSON.parse(sessionStorage.getItem('usuario'));
    const pedidos = JSON.parse(sessionStorage.getItem('pedidos'));
    const userInfo = document.getElementById('informacion')
    const userHistorial = document.getElementById('historial')

    const url = `https://xanerie.onrender.com/admin/users`

    fetch(url)
        .then(response => response.json())
        .then(data => {

            if (usuario) {
                userInfo.innerHTML = `
                <h3>Información</h3>
                <form id="formulario">
                    <div class="mb-3">
                        <label for="inputName" class="form-label">Nombre:</label>
                        <input type="text" class="form-control" id="inputName" placeholder="${usuario.nombre}">
                    </div>
                    <div class="mb-3">
                        <label for="inputApellido" class="form-label">Apellido:</label>
                        <input type="text" class="form-control" id="inputApellido" placeholder="${usuario.apellido}">
                    </div>
                    <div class="mb-3">
                        <label for="inputEmail1" class="form-label">Email:</label>
                        <input type="email" class="form-control" id="inputEmail" placeholder="${usuario.email}">
                    </div>
                    <div class="mb-3">
                        <label for="InputTelefono" class="form-label">Telefono:</label>
                        <input type="tel" class="form-control" id="inputTelefono" placeholder="${usuario.telefono}">
                    </div>
                    <div class="mb-3">
                        <label for="inputPassword" class="form-label">Cambia tu contraseña:</label>
                        <input type="password" class="form-control" id="inputPassword" placeholder="contraseña">
                    </div>
                    <div class="mb-3">
                        <label for="inputId" class="form-label">Digita "${usuario.id}" para editar tus datos: </label>
                        <input type="number" class="form-control" id="inputId" placeholder="${usuario.id}">
                    </div>
                    <button type="submit" class="cafe" id="button-user">Editar</button>
                </form>
                        `
                const formulario = document.getElementById('formulario')
                const id = document.getElementById('inputId')
                const nombre = document.getElementById('inputName')
                const apellido = document.getElementById('inputApellido')
                const email = document.getElementById('inputEmail')
                const telefono = document.getElementById('inputTelefono')
                const password = document.getElementById('inputPassword')


                const campos = [
                    { input: id, regex: /^\d{1,7}$/, mensaje: 'Por favor, digita el número mostrado' },
                    { input: nombre, regex: /^[a-zA-ZÀ-ÿ\s]{1,40}$/i, mensaje: 'Ingresa un nombre válido' },
                    { input: apellido, regex: /^[a-zA-ZÀ-ÿ\s]{1,40}$/i, mensaje: 'Ingresa un apellido válido' },
                    { input: email, regex: /^[^\s@]+@[^\s@]+\.[^\s@]+$/, mensaje: 'Ingresa un email válido' },
                    { input: telefono, regex: /^\d{8,}$/, mensaje: 'El telefono solo debe contener números' },
                    { input: password, regex: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+])[A-Za-z\d!@#$%^&*()_+]{8,}$/, mensaje: 'La contraseña debe tener 8 caracteres y contener al menos una mayúscula, una minúscula, un número y un caracter especial (!@#$%^&*()_+).' }
                ];
                const noValidado = (input, mensaje) => {
                    const formControl = input.parentElement;
                    
                    // Verificar si ya hay un aviso
                    const aviso = formControl.querySelector('p.alert', 'p.alert-danger');
                    if (!aviso) {
                        const nuevoAviso = document.createElement("p");
                        nuevoAviso.innerText = mensaje;
                        nuevoAviso.classList.add('alert', 'alert-danger');
                        formControl.appendChild(nuevoAviso);
                    }
                }
                const validarCampos = () => {
                    if (id.value == '' || id.value != usuario.id) {
                        noValidado(id, 'Por favor, digita el número mostrado');
                        return false;
                    }
                    const camposValidos = campos.every(({ input, regex, mensaje }) => {
                        const valor = input.value.trim();
                        if (valor != '' && !regex.test(valor)) {
                            noValidado(input, mensaje);
                            return false;
                        }
                        return true;
                    });
                    return camposValidos;
                };

                formulario.addEventListener('submit', async (e) => {
                    e.preventDefault();

                    const camposSonValidos = validarCampos();

                    if (camposSonValidos) {

                        try {
                            const user = {
                                id: id.value,
                                nombre: nombre.value !== '' ? nombre.value : usuario.nombre,
                                apellido: apellido.value !== '' ? apellido.value : usuario.apellido,
                                email: email.value !== '' ? email.value : usuario.email,
                                telefono: telefono.value !== '' ? telefono.value : usuario.telefono,
                                password: password.value !== '' ? password.value : usuario.password
                            };
                            const urlPut = `https://xanerie.onrender.com/admin/users/${id.value}`;
                            const response = await fetch(urlPut, {
                                method: 'PUT',
                                headers: {
                                    'Content-Type': 'application/json'
                                },
                                body: JSON.stringify(user)
                            });

                            const usuarioEditado = {
                                id: id.value,
                                nombre: nombre.value !== '' ? nombre.value : usuario.nombre,
                                apellido: apellido.value !== '' ? apellido.value : usuario.apellido,
                                email: email.value !== '' ? email.value : usuario.email,
                                telefono: telefono.value !== '' ? telefono.value : usuario.telefono,
                                password: password.value !== '' ? password.value : usuario.password
                            };
    
                            sessionStorage.setItem('usuario', JSON.stringify(usuarioEditado))
                            
                            const data = await response.json();
                            console.log(data);
                        } catch (error) {
                            console.log('Error al editar usuario:', error);
                        }

                        const mostrarFormularioEnviado = () => {
                            const formularioEnviado = document.getElementById("formularioEnviado");
                            formularioEnviado.style.display = "block";
                        }; mostrarFormularioEnviado()
                        setTimeout(() => {
                            location.reload()
                        }, 1500);
                    }
                });
            }



            if (pedidos) {
                pedidos.forEach(pedido => {
                    const nuevoPedido = document.createElement('div');
                    nuevoPedido.classList.add('pedidos');
                    nuevoPedido.innerHTML = `
                        <hr>
                        <h5>Pedido: #${pedido.id_pedido}</h5>
                        <div class="detallesPedido item-producto" style="display: none;">
                            <p>${pedido.productosCarrito.map(producto => `<div id="img"> <img src="${producto.imagen}" id="img"></div>  ${producto.nombre} <b> × ${producto.cantidad}</b>`).join('<br><br>')}</p>
                            <h5>Total: $${pedido.totalPrecio.toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ',')}</h5>
                        </div>
                        <button class="cafe-1 mostrarDetalles">Mostrar detalles</button>
                    `;
                    userHistorial.appendChild(nuevoPedido);
                });
                // Boton para mostrar/ocultar detalles
                userHistorial.addEventListener('click', (event) => {
                    if (event.target.classList.contains('mostrarDetalles')) {
                        const detallesPedido = event.target.previousElementSibling; // Buscar el elemento hermano anterior
                        if (detallesPedido.style.display === 'none') {
                            detallesPedido.style.display = 'block';
                            event.target.innerText = 'Ocultar detalles';
                        } else {
                            detallesPedido.style.display = 'none';
                            event.target.innerText = 'Mostrar detalles';
                        }
                    }
                });
            }
        });

})