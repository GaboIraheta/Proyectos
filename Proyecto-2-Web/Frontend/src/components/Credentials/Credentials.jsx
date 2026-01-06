import React, { useContext, useState } from 'react'
import './Credentials.css';
import useUpdate from '../../Hooks/UseUpdate';
import config from '../../../config';
import { LoginContext, LoginProvider } from '../../Context/LoginContext';
import useUtil from '../../Hooks/useUtil';

const CredentialsProvider = () => {

    const [email, setEmail] = useState("");
    const [username, setUsername] = useState("");
    const [message, setMessage] = useState("");
    const [isValid, setIsValid] = useState(true);

    const { user, token, role, updateUserField } = useContext(LoginContext);

    const { verifyRole } = useUtil();
    const { updateData, error, loading } = useUpdate(`${config.API_URL}/${localStorage.getItem('verify')}/update-credentials`);

    const handleUpdate = async (event) => {
        event.preventDefault();
        setMessage("");
        setIsValid(true);

        if (email === user.email) {
            setMessage("El correo electrónico ingresado coincide con el actual.");
            setIsValid(false);
            return;
        } else if (username === email.username) {
            setMessage("El nombre de usuario ingresado coincide con el actual.");
            setIsValid(false);
            return;
        } else if (!email && !username) {
            setMessage("Se requiere el ingreso de correo electrónico nuevo y usuario.");
            setIsValid(false);
            return;
        }

        console.log(localStorage.getItem('verify'));
        await verifyRole(user.email);
        console.log(localStorage.getItem('verify'));

        const response = await updateData(sessionStorage.getItem('id'), { username, email }, token, role);

        if (error || !response) return;
        
        setMessage(response.message);

        setUsername(response.data.username);
        updateUserField('username', response.data.username);

        setEmail(response.data.email);
        updateUserField('email', response.data.email);
    }
    return (
        <form className="formContainer-cred" onSubmit={handleUpdate}>
            <div className="headerWrapper-cred">
                <div><img
                    loading="lazy"
                    src="https://cdn.builder.io/api/v1/image/assets/TEMP/3031ae1a0ef5122343cfc046a0fe7378694ac59107f049ac12acaeaba50c1463?placeholderIfAbsent=true&apiKey=dc5b04b1bd644122995a9f89f3c5e2ef"
                    className="logo-cred"
                    alt="Company logo"
                /></div>
                <div><h1 className="title-cred">Cambiar Credenciales</h1></div>
            </div>
            <div className="formsfields-cred">

                <div className="formField-cred">
                    <label htmlFor="email" className="formLabel-cred">
                        Nuevo Usuario
                    </label>
                    <input
                        type="text"
                        id="nuser"
                        className="formInput-cred"
                        value={username}
                        onChange={(event) => setUsername(event.target.value)}
                        required
                    />
                </div>
                <div className="formField-cred">
                    <label htmlFor="email" className="formLabel-cred">
                        Nuevo correo electrónico
                    </label>
                    <input
                        type="text"
                        id="email"
                        className="formInput-cred"
                        value={email}
                        onChange={(event) => setEmail(event.target.value)}
                        required
                    />
                </div>

                <div className="socialLoginContainer-cred">
                    <button type="submit" className="button google">
                        {loading ? "Actualizando..." : "Actualizar"}
                    </button>
                </div>
                {message && <p style={{ color: isValid ? "green" : "red"}}>{message}</p>}
                {error && <p style={{ color: "red" }}>{error}</p>}
                <div>
                    <button className='btn-back' onClick={() => localStorage.getItem('verify') ? window.location.href = '/admin' : window.location.href = '/profile'}>
                        Regresar
                    </button>
                </div>
            </div>
        </form>

    );
}

const Credentials = () => {
    return (
        <>
            <LoginProvider>
                <CredentialsProvider/>
            </LoginProvider>
        </>
    )
}

export default Credentials
