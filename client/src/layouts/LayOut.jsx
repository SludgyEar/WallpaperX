import './styles/LayOut.css';
export default function LayOut({ children }) {
    return (
        <div className="main-container">
            <div className="main-header">
                <h1>WallpaperX</h1>
                <nav className="nav">
                    <ul>
                        <li><a href="/">Home</a></li>
                        <li><a href="/login">Login</a></li>
                    </ul>
                </nav>
            </div>
            <main className="main-content">
                {children}
            </main>
            <footer className="main-footer">
                <p>&copy; 2023 Uploads Manager</p>
            </footer>
        </div>
    );
}