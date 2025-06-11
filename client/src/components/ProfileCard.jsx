
export default function ProfileCard ( { user } ) {

    const {  userName, email, state, rol } = user;

    return (
        <div>
            <div className="card">
                    <div className="form-group">
                        <label>Nombre:</label>
                        <input type="text" defaultValue={userName} />
                    </div>
                    <div className="form-group">
                        <label>Correo:</label>
                        <input type="email" defaultValue={email} />
                    </div>
                    <div className="form-group">
                        <label>Status:</label>
                        <select defaultValue={state}>
                            <option value="active">Activo</option>
                            <option value="inactive">Inactivo</option>
                        </select>
                    </div>
                    <div className="form-group">
                        <label>Rol:</label>
                        <select defaultValue={rol}>
                            <option value="admin">Administrador</option>
                            <option value="user">Usuario</option>
                        </select>
                    </div>
            </div>
        </div>
    );
}