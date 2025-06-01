import { useContext, createContext, useState, useEffect } from "react";

const AuthContext = createContext({
    isAuth: false
});

export function AuthProvider({ children }) {
    const [isAuth, setIsAuth] = useState(false);

    return (
        <AuthContext.Provider value={{ isAuth }}>
            {children}
        </AuthContext.Provider>
    );
}

export const useAuth = () => useContext(AuthContext);