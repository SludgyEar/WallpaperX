import { useEffect, useState } from 'react';
import '../layouts/styles/AdminCrud.css';
import axios from 'axios';
import EditCard from './EditCard';

function AdminCrud() {

    const [filtro, setFiltro] = useState('id');
    const handleFiltro = (e) => {
        setFiltro(e.target.name);
    };

    const [userList, setUserList] = useState([]);
    const getUserList = async () => {
        try{
            const response = await axios.get('http://localhost:8081/api/user/allUsers');
            setUserList(response.data); 
        }catch (error) {console.log("Ha ocurrido un problema en AdminCrud ",error);}
    };

    useEffect(() => {
        getUserList();
    },[]);

    const [valueToFind, setValueToFind] = useState('');
    const handleValueToFind = (e) => {
        setValueToFind(e.target.value);
    };
    const handleSearchByFilter = async (e) => {
        e.preventDefault();
        try{
            switch(filtro){
                case 'id':
                    const responseById = await axios.get(`http://localhost:8081/api/user/userById/${valueToFind}`);
                    // Asegurarse de que la respuesta sea un array
                    const dataById = Array.isArray(responseById.data) ? responseById.data : [responseById.data];
                    setUserList(dataById);
                    break;
                case 'nombre':
                    const responseByName = await axios.get(`http://localhost:8081/api/user/userByName/${valueToFind}`);
                    const dataByName = Array.isArray(responseByName.data) ? responseByName.data : [responseByName.data];
                    setUserList(dataByName);
                    break;
                case 'correo':
                    const responseByEmail = await axios.get(`http://localhost:8081/api/user/userByEmail/${valueToFind}`);
                    const dataByEmail = Array.isArray(responseByEmail.data) ? responseByEmail.data : [responseByEmail.data];
                    setUserList(dataByEmail);
                    break;
                case 'status':
                    const responseByStatus = await axios.get(`http://localhost:8081/api/user/userByState/${valueToFind}`);
                    setUserList(responseByStatus.data);
                    break;
                case 'rol':
                    const responseByRole = await axios.get(`http://localhost:8081/api/user/userByRol/${valueToFind}`);
                    setUserList(responseByRole.data);
                    break;
                default:
                    getUserList(); // Si no se selecciona un filtro válido, se obtienen todos los usuarios
                    break;
                    
            }
        }catch(err) { console.log("Error al buscar por filtro", err); }
    };

    // Manejo de estado para el overlay
    const [shOverlay, setShOverlay] = useState(true);
    const toggleOverlay = () => {
        setShOverlay(!shOverlay);
    };

    const [userToUpdate, setUserToUpdate] = useState({});
    const handleUserToUpdate = (user) => {
        setUserToUpdate(user);
    };



    return (
        <div className='crud-container'>
            <h2>Filtrar Usuarios</h2>

            {shOverlay && (
                <div>
                    <div className='overlay'>
                        <div className="overlay-content">
                            {userToUpdate && (
                                <EditCard user={userToUpdate} />
                            )}
                            <button onClick={() => {
                                toggleOverlay();
                                setUserToUpdate({});
                            }}>Cancelar</button>
                        </div>
                    </div>
                </div>
            )}

            <div className="button-wrapper">
                <button className="filtro-select" onClick={handleFiltro} name="id">Id</button>
                <button className="filtro-select" onClick={handleFiltro} name="nombre">Nombre</button>
                <button className="filtro-select" onClick={handleFiltro} name="correo">Correo</button>
                <button className="filtro-select" onClick={handleFiltro} name="status">Estado</button>
                <button className="filtro-select" onClick={handleFiltro} name="rol">Rol</button>
                <button className="filtro-select" onClick={() => getUserList()} name="todos">Todos</button>
            </div>
            <form className='filtro-form' onSubmit={handleSearchByFilter}>
                <input type="text" placeholder={`Buscar por ${filtro}`} className="filtro-input" onChange={handleValueToFind} required/>
                <button className="filtro-select">Buscar</button>
            </form>
            <div className='crud-table'>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Correo</th>
                            <th>Status</th>
                            <th>Rol</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        {Array.isArray(userList) && userList.map((user) => (
                            <tr key={user.id}>
                                <td>{user.id}</td>
                                <td>{user.userName}</td>
                                <td>{user.email}</td>
                                <td>{user.state === 1 ? 'ACTIVO' : user.state === 0 ? 'INACTIVO' : 'BANEADO'}</td>
                                <td>{user.rol}</td>
                                <td>
                                    <button className='action-button' id='update-button'
                                        onClick={async () => {
                                            try{
                                                toggleOverlay();
                                                const response = await axios.get(`http://localhost:8081/api/user/userById/${user.id}`);
                                                handleUserToUpdate(response.data);
                                                console.log("Usuario a actualizar:", response.data);
                                            } catch(err) { console.log("Error al actualizar usuario", err); }
                                        }}
                                    >
                                        Update
                                    </button>
                                    <button className='action-button' id='details-button'>Details</button>
                                    <button className='action-button' id='delete-button'>Delete</button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default AdminCrud;