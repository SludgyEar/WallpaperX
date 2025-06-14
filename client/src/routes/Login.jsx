import '../layouts/styles/Login.css';
import { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useAuth } from "../providers/AuthProvider";

const apiUrl = process.env.REACT_APP_API_URL;

export default function Login() {
    const navigate = useNavigate();
    const auth = useAuth();
    
    
    const [user, setUser] = useState({
        userName: '',
        password: ''
    });
    const handleSetUser = (e) => {
        setUser(prev => ({ ...prev, [e.target.name]: e.target.value }));
    };
    const handleSubmit = async (e) => {
        e.preventDefault();
        try{
            const response = await axios.post(`http://localhost:8081/api/user/auth`, user);
            if(response.status === 201){
                (response.data.rol) === 'ADMIN' ? auth.handleAdmin(true) : auth.handleAdmin(false);
                auth.handleUser(response.data);
                auth.handleAuth(true);
                navigate('/dashboard');
            }
        }catch (error) { console.error("Error al acceder como usuario", error); }
    };

    return (
        <div className="login-container">
            <form className="login-form" onSubmit={handleSubmit}>
                <h1>Iniciar sesión</h1>
                <div>
                    <label htmlFor="email">Username:</label>
                    <input type="text" required name='userName'onChange={handleSetUser}/>
                </div>
                <div>
                    <label htmlFor="password">Password</label>
                    <input type="password" name='password' required onChange={handleSetUser}/>
                </div>
                <button type="submit">Iniciar sesión</button>
            </form>
            <a href="/signup">¿No tienes cuenta?</a>
        </div>
    );
}