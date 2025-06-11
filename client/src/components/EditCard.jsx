import '../layouts/styles/EditCard.css';
import { useState } from 'react';
import axios from 'axios';

export default function EditCard({ user, onClose, onClean }) {
    
    const {email, id, password, registeredDate, rol, state, userName} = user;

    const [updatedUser, setUpdatedUser] = useState({});
    const handleUpdatedUser = (e) => {
        setUpdatedUser(prev => ({ ...prev, [e.target.name]: e.target.value }));
    };

    const getFieldValue = (fieldName) => {
        return updatedUser[fieldName] || user[fieldName];
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try{
            await axios.put(`http://localhost:8081/api/user/modify/${id}`, updatedUser);
            onClean();
            onClose();
        } catch(err){ console.log("Error al actualizar el usuario:", err); }
    };

    return (
        
        <div className="edit-card">
            <form className='edit-form' onSubmit={handleSubmit}>
                <h2>Editar Usuario</h2>
                <div className="edit-form-group">
                    <label>Nombre</label>
                    <input type="text" placeholder={userName} name='userName'onChange={handleUpdatedUser}/>
                </div>
                <div className="edit-form-group">
                    <label>Password</label>
                    <input type="password" placeholder={password} name='password' onChange={handleUpdatedUser}/>
                </div>
                <div className="edit-form-group">
                    <label>Status</label>
                    <select name="state" value={getFieldValue('state')} onChange={handleUpdatedUser}>
                        <option value='1'>Activo</option>
                        <option value='0'>Inactivo</option>
                        <option value='2'>Baneado</option>
                    </select>
                </div>
                <div className="edit-form-group">
                    <label>Rol</label>
                    <select name="rol" value={getFieldValue('rol')} onChange={handleUpdatedUser}>
                        <option value="USER">Usuario</option>
                        <option value="ADMIN">Administrador</option>
                    </select>
                </div>
                <div className="edit-button-action">
                    <button type='submit'>Guardar Cambios</button>
                    <button onClick={onClose} id='edit-cancel-button'>Cancelar</button>
                </div>
            </form>
        </div>
        
    );
}