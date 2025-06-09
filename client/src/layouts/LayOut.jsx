import './styles/LayOut.css';
export default function LayOut({ children }) {
    return (
        <div className="main-container">
            <header className="main-header">
                <h1>WallpaperX</h1>
            </header>
            <main className="main-content">
                {children}
            </main>
            <footer className="main-footer">
                <p>&copy; 2025 WallpaperX</p>
            </footer>
        </div>
    );
}