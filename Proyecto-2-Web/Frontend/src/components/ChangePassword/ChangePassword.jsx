import React, { useContext, useEffect, useState } from "react";
import usePost from "../../Hooks/UsePost";
import { LoginContext, LoginProvider } from "../../Context/LoginContext.jsx";
import API_URL from "../../../config.js";
import './ChangePassword.css'
import config from "../../../config.js";
import useUtil from "../../Hooks/useUtil.jsx";
import useUpdate from "../../Hooks/UseUpdate.jsx";

const ChangePasswordProvider = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirm, setConfirm] = useState("");
    const [message, setMessage] = useState("");
    const [isValid, setIsValid] = useState(true);

    const { verifyRole } = useUtil();
    
    const { user, token, role } = useContext(LoginContext);
    const { recoverPassword, error, loading } = usePost(`${config.API_URL}/${localStorage.getItem('verify')}`);
    const { updateData, error: errorUpdate, loading: loadingUpdate } = useUpdate(`${config.API_URL}/${localStorage.getItem('verify')}/change-password`);

    const handleUpdate = async (event) => {
        event.preventDefault();
        setMessage("");
        setIsValid(true);

        console.log(localStorage.getItem('verify'));
        await verifyRole(email);
        console.log(localStorage.getItem('verify'));
        if (localStorage.getItem('isRecovery')) {
            if ((!email && !password)) {
                setMessage('Se requiere ingreso de correo electronico y contraseña.');
                setIsValid(false);
                return;
            } else if (password !== confirm) {
                setMessage('Las contraseñas ingresadas no coinciden.');
                setIsValid(false);
                return;
            } if (localStorage.getItem('verify') !== 'user' && localStorage.getItem('verify') !== 'admin') {
                setMessage('Ha ocurrido un error. Intenta de nuevo.');
                setIsValid(false);
                return;
            }
    
            const response = await recoverPassword(email, password);
    
            if (error || !response) {
                setIsValid(false);
                return;
            }
    
            console.log(response.message);
            setMessage(response.message);
        } else {
            if (!email && !password) {
                setMessage('Se requiere ingreso de correo electronico y contraseña.');
                setIsValid(false);
                return;
            } else if (password !== confirm) {
                setMessage('Las contraseñas ingresadas no coinciden.');
                setIsValid(false);
                return;
            } else if (email !== user.email) {
                setMessage('El correo electrónico ingresado no corresponde al correo electrónico de la sesión.');
                setIsValid(false);
                return;
            } else if (password == user.password) {
                setMessage('La contraseña nueva debe ser diferente a la anterior.');
                setIsValid(false);
                return;
            } else if (localStorage.getItem('verify') !== 'user' && localStorage.getItem('verify') !== 'admin') {
                setMessage('Ha ocurrido un error. Intenta de nuevo.');
                setIsValid(false);
                return;
            }
            console.log(email);
            console.log(user.email);
    
            const response = await updateData(sessionStorage.getItem('id'), { newPassword: password }, token, role);
            if (errorUpdate || !response) return;
            setMessage(response.message);
        }
        localStorage.removeItem('verify');
    }

    return (
        <form className="formContainer-change" onSubmit={handleUpdate}>
            <div className="headerWrapper-change">
                <div><img
                    loading="lazy"
                    src="https://cdn.builder.io/api/v1/image/assets/TEMP/3031ae1a0ef5122343cfc046a0fe7378694ac59107f049ac12acaeaba50c1463?placeholderIfAbsent=true&apiKey=dc5b04b1bd644122995a9f89f3c5e2ef"
                    className="logo-change"
                    alt="Company logo"
                /></div>
                <div><h1 className="title-change">Recuperar contraseña</h1></div>
            </div>
            <div className="formsfields-change">
                <div className="formField-change">
                    <label htmlFor="email" className="formLabel-change">
                        Email
                    </label>
                    <input
                        type="text"
                        id="email"
                        className="formInput-change"
                        value={email}
                        onChange={(event) => setEmail(event.target.value)}
                        required
                    />
                </div>

                <div className="formField-change">
                    <label htmlFor="password" className="formLabel-change">
                        Contraseña
                    </label>
                    <input
                        type="password"
                        id="password"
                        className="formInput-change"
                        value={password}
                        onChange={(event) => setPassword(event.target.value)}
                        required
                    />
                </div>

                <div className="formField-change">
                    <label htmlFor="confirmPassword" className="formLabel-change">
                        Ingresa de nuevo la contraseña
                    </label>
                    <input
                        type="password"
                        id="confirmPassword"
                        className="formInput-change"
                        value={confirm}
                        onChange={(event) => setConfirm(event.target.value)}
                        required
                    />
                </div>
            </div>
            <div className="socialLoginContainer-change">
                <button type="submit" className="button google" disabled={loading}>
                    {!localStorage.getItem('isRecovery') ? (loading ? "Recuperando..." : "Recuperar contraseña") : (loadingUpdate ? "Actualizando" : "Actualizar contraseña")}
                </button>
            </div>
            {message && <p style={{ color: isValid ? "green" : "red"}}>{message}</p>}
            {error && <p style={{ color: "red" }}>{error}</p>}
            <div>
                <button className='btn-back' onClick={() => !localStorage.getItem('isRecovery') ? (window.location.href = '/login') : (localStorage.getItem('verify') ? window.location.href = '/admin' : window.location.href = '/profile')}>
                    Regresar
                </button>
            </div>
        </form>
    );
};

const ChangePassword = () => {
    return (
        <>
            <LoginProvider>
                <ChangePasswordProvider/>
            </LoginProvider>
        </>
    )
}

export default ChangePassword;
