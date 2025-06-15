import AdminCrud from "../components/AdminCrud";
import ProfileCard from "../components/ProfileCard";
import Wallpapers from "../components/Wallpapers";
import LayOut from "../layouts/LayOut";
import '../layouts/styles/Dashboard.css'
import { useAuth } from "../providers/AuthProvider";
import { useState } from "react";

export default function Dashboard() {
    const auth = useAuth();

    const [selectedService, setSelectedService] = useState('inicio');
    const handleServiceClick = (service) => {
        setSelectedService(service);
    };
    return (
        <LayOut>
            <div className="dashboard-container">
                <div className="sidebar">
                    <button className={selectedService === 'inicio' ? 'active' : ''} onClick={() => handleServiceClick('inicio')}>
                        Inicio
                    </button>
                    <button className={selectedService === 'wallpapers' ? 'active' : ''} onClick={() => handleServiceClick('wallpapers')}>
                        Wallpapers
                    </button>
                    <button className={selectedService === 'perfil' ? 'active' : ''} onClick={() => handleServiceClick('perfil')}>
                        Perfil
                    </button>
                    <button className={selectedService === 'configuracion' ? 'active' : ''} onClick={() => handleServiceClick('configuracion')}>
                        Configuración
                    </button>
                    {auth.isAdmin && (
                        <button className={selectedService === 'admin' ? 'active' : ''} onClick={() => handleServiceClick('admin')}>
                            Panel de Usuarios
                        </button>
                    )}
                </div>
                
                        
                {selectedService === 'inicio' && (
                    <div className="card">
                        <h2>Bienvenido, {auth.user.userName}!</h2>
                        <p>Esta es la página de inicio para un usario. Su rol es {auth.user.rol}.</p>
                        <p>Por lo que, actualmente su privilegio de administrador es {auth.isAdmin ? 'TRUE' : 'FALSE'}</p>
                    </div>
                )}
                {selectedService === 'perfil' && (
                    <div className="card">
                        <h2>Bienvenido, {auth.user.userName}!</h2>
                        <p>Este es tu perfil.</p>
                        <ProfileCard user={auth.user} />
                    </div>
                )}
                {selectedService === 'configuracion' && (
                    <div className="card">
                        <h2>Bienvenido, {auth.user.userName}!</h2>
                        <p>Esta es la página de configuración</p>
                    </div>
                )}
                {selectedService === 'admin' && auth.isAdmin && (
                    <div className="card">
                        <AdminCrud/>
                    </div>
                )}
                {selectedService === 'wallpapers' && (
                    <div className="card">
                        <h2>Hola {auth.user.userName}, esta es la sección de Wallpapers.</h2>
                        <Wallpapers />
                    </div>
                )}
            </div>
        </LayOut>
    );
}