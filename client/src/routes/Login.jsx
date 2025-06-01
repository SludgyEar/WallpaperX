import '../layouts/styles/Login.css';
import { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

export default function Login() {
    const navigate = useNavigate();
    
    const [user, setUser] = useState({
        userName: '',
        password: ''
    });
    const handleSetUser = (e) => {
        setUser(prev => ({ ...prev, [e.target.name]: e.target.value }));
    }
    const handleSubmit = (e) => {
        e.preventDefault();
        try{
            const response = axios.post('http://localhost:8081/api/user/auth', user);
            navigate('/test');
        }catch (error) { console.error("Error during login:", error); }
    }

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
        </div>
    );
}