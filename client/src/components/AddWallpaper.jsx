import { useState } from 'react';
import '../layouts/styles/Wallpapers.css';
import axios from 'axios';

export default function AddWallpaper({ user}) {
    const { email, id, password, registeredDate, rol, state, userName } = user;
    const [shOverlay, setShowOverlay] = useState(false);
    const toggleOverlay = () => {
        setShowOverlay(!shOverlay);
    };

    const [title, setTitle] = useState('');
    const handleTitle = (e) => {
        setTitle(e.target.value)
    };
    const [description, setDescription] = useState('');
    const handleDescription = (e) => {
        setDescription(e.target.value)
    };
    const [file, setFile] = useState(null);
    const handleFile = (e) => {
        const selectedFile = e.target.files[0];
        const maxSize = 20 * 1024 * 1024; // 20 MB
        if(selectedFile.size > maxSize) {
            alert('El archivo es demasiado grande. El tamaño máximo permitido es 20 MB.');
            return;
        }
        // setSize(`${(selectedFile.size / (1024 * 1024)).toFixed(2)} MB`); // Convertir a MB y redondear a 2 decimales - Lo hace el back
        setFile(e.target.files[0]);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const formData = new FormData();
        formData.append('file', file);

        const wallpaper = {
            title: title,
            description: description,
            state: state,
            user: { id: id }
        }

        formData.append('wallpaper', JSON.stringify(wallpaper));
        try {
            const response = await axios.post('http://localhost:8081/api/wallpaper/upload', formData, {
                headers: { 'Content-Type': 'multipart/form-data' }
            });
            alert('Wallpaper subido correctamente');
            toggleOverlay(); // Cerrar el overlay después de subir el wallpaper
            
        } catch (error) {
            console.error('Error al subir el wallpaper', error);
            alert('Error al subir el wallpaper. Por favor, inténtalo de nuevo.');
        }
    };

    return (
        <div className="add-wallpaper-container">
            <button onClick={toggleOverlay}>Agregar Wallpaper</button>

            {shOverlay && (
                <div className='overlay'>
                    <div className="overlay-content">
                        <h2>Agregar Wallpaper</h2>
                        <form className='add-wallpaper-from' onSubmit={handleSubmit}>
                            <div className="add-wallpaper-form-group">
                                <label>Título:</label>
                                <input type="text" name="title" required onChange={handleTitle} />
                            </div>
                            <div className="add-wallpaper-form-group">
                                <label>Descripción</label>
                                <textarea name="description" onChange={handleDescription}/>
                            </div>
                            <div className="add-wallpaper-form-group">
                                <div className="file-upload">
                                    <input type="file" id="upload" onChange={handleFile}/>
                                    <label htmlFor='upload'>Seleccionar archivo</label>
                                </div>
                            </div>
                            <div className="add-wallpaper-buttons">
                                <button type="submit" id='add-wallpaper-button'>Subir Wallpaper</button>
                                <button onClick={toggleOverlay}>Cerrar</button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
}