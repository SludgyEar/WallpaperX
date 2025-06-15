import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useAuth } from '../providers/AuthProvider';
import AddWallpaper from './AddWallpaper';

export default function Wallpapers() {
    const [wallpapers, setWallpapers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(false);
    const auth = useAuth();


    useEffect(() => {
        const fetchWallpapers = async () => {
            try {
                // const response = await axios.get('http://localhost:8081/api/wallpaper/allWallpapers'); // Carga todos los wallpapers
                const response = await axios.get(`http://localhost:8081/api/wallpaper/wallapersFromUser/${auth.user.id}`);  // Carga los wallpapers del usuario autenticado
                setWallpapers(response.data);
                console.log("Wallpapers cargados:", response.data);
            } catch (error) {
                setError(true);
            } finally {
                setLoading(false);
            }
        };

        fetchWallpapers();
    }, []);

    if (loading) return <div className="text-center p-10">Cargando wallpapers...</div>;
    if (error) return <div className="text-center p-10 text-red-500">Error al cargar los wallpapers.</div>;

    return (
        <>
            <div className="p-6">
                <h2 className="text-2xl font-bold mb-4">Galería de Wallpapers</h2>
                {wallpapers.length === 0 ? (
                    <p className="text-gray-500">No hay wallpapers disponibles aún.</p>
                ) : (
                    <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
                        {wallpapers.map((wallpaper) => (
                            <div key={wallpaper.id} className="rounded overflow-hidden shadow-lg">
                                <img
                                    src={`http://localhost:8081${wallpaper.filePath}`} // Asegúrate de que la ruta sea correcta
                                    alt={wallpaper.title || 'Wallpaper'}
                                    style={{ width: '25%', height: '25%', border_radius: '8px'}}
                                />
                                <div className="px-4 py-2 bg-white">
                                    <h3 className="text-lg font-semibold truncate">{wallpaper.title || 'Sin título'}</h3>
                                    <p className="text-gray-700 text-sm mt-1">{wallpaper.description || 'Sin descripción'}</p>
                                    <p className="text-sm text-gray-600 truncate" >{wallpaper.size}</p>
                                    <p className="text-sm text-gray-600 truncate">Subido por: {wallpaper.user.userName || 'Anónimo'}</p>
                                </div>
                            </div>
                        ))}
                    </div>
                )}
            </div>
            <AddWallpaper user={auth.user}/>
        </>
    );
}
