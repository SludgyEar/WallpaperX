import { useState } from 'react';
import '../layouts/styles/AddUserCard.css';
import axios from 'axios';

function AddUserCard({ onClose }) {
    const [newUser, setNewUser] = useState({
        userName: '',
        email: '',
        password: '',
        state: '1', // Activo por defecto
        rol: 'USER' // Usuario por defecto
    });

    const handleNewUser = (e) => {
        const { name, value } = e.target;
        setNewUser(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try{
            const response = await axios.post(`http://localhost:8081/api/user/register`, newUser);
            if(response.status === 201){
                onClose(); // Cerrar el formulario después de crear el usuario
                setNewUser({
                    userName: '',
                    email: '',
                    password: '',
                    state: '1',
                    rol: 'USER'
                }); // Reset the user
                
            }
        }catch(erro){ console.log("Error creando usuario a través del crud",erro) }
        
    };

    return (
        <div className="add-card">
            <form className='add-form' onSubmit={handleSubmit}>
                <h2>Agregar Usuario</h2>
                <div className="add-form-group">
                    <label>Nombre</label>
                    <input type="text" placeholder='Username' name='userName' onChange={handleNewUser} required/>
                </div>
                <div className="add-form-group">
                    <label>Email</label>
                    <input type="email" placeholder='Email' name='email' onChange={handleNewUser} required/>
                </div>
                <div className="add-form-group">
                    <label>Password</label>
                    <input type="password" placeholder='Password' name='password' onChange={handleNewUser} required/>
                </div>
                <div className="add-form-group">
                    <label>Status</label>
                    <select name="state" onChange={handleNewUser} value={newUser.state}>
                        <option value='1'>Activo</option>
                        <option value='0'>Inactivo</option>
                        <option value='2'>Baneado</option>
                    </select>
                </div>
                <div className="add-form-group">
                    <label>Rol</label>
                    <select name="rol" onChange={handleNewUser} value={newUser.rol}>
                        <option value="USER">Usuario</option>
                        <option value="ADMIN">Administrador</option>
                    </select>
                </div>
                <div className="add-button-action">
                    <button type='submit'>Guardar Cambios</button>
                    <button onClick={onClose} id='add-cancel-button'>Cancelar</button>
                </div>
            </form>
        </div>
    );
}

export default AddUserCard;