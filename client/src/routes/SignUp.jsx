import { useState } from "react";
import { resolvePath, useNavigate } from "react-router-dom";
import axios from "axios";
import '../layouts/styles/SignUp.css';
import { useAuth } from "../providers/AuthProvider";

const apiUrl = process.env.REACT_APP_API_URL;

export default function SignUp() {
    const navigate = useNavigate();
    const auth = useAuth();

    const [user, setUser] = useState({
        userName: '',
        email: '',
        password: '',
        rol: 'USER',    // String (ADMIN or USER)
        state: 1      // int (1 for active, 0 for inactive)
    });
    const handleSetUser = (e) => {
        setUser(prev => ({ ...prev, [e.target.name]: e.target.value }));
    };
    const handleSubmit = async (e) => {
        e.preventDefault();
        try{
            const response = await axios.post(`http://localhost:8081/api/user/register`, user);
            if(response.status === 201){
                auth.handleAuth(true);
                auth.handleUser(response.data);
                navigate('/dashboard');
            }   // No se tiene que validar dentro del if un status erróneo, ya que si no se cumple el status 201, se lanzará un error y se capturará en el catch

        }catch (error){
            console.log("Ha ocurrido un error al crear el usuario", error);
            alert("Error al crear el usuario. Por favor, inténtalo de nuevo.");
            e.target.reset(); // Limpiar el formulario en caso de error
            setUser({ userName: '', email: '', password: '', rol: 'USER', state: 1 }); // Reiniciar el estado del usuario
            // Lo mejor será desplegar un mensaje modal para indicar el error al usuario
            // y no un alert, ya que este último es muy intrusivo y no se ve bien en la UI.
        }
    };

    return (
        <div className="register-container">
            <form className="register-form" onSubmit={handleSubmit}>
                <h1>Registro de Usuario</h1>
                <div>
                    <label htmlFor="userName">Username:</label>
                    <input type="text" required name='userName' onChange={handleSetUser} />
                </div>
                <div>
                    <label htmlFor="email">Email:</label>
                    <input type="email" required name='email' onChange={handleSetUser} />
                </div>
                <div>
                    <label htmlFor="password">Password</label>
                    <input type="password" name='password' required onChange={handleSetUser} />
                </div>
                <button type="submit">Registrarse</button>
                <button id="register-cancel" onClick={() => navigate("/")}>Cancelar</button>
            </form>
        </div>
    );
}