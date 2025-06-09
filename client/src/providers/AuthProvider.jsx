import { useContext, createContext, useState, useEffect } from "react";

const AuthContext = createContext({
    isAuth: false,
    handleAuth: () => {},
    user: {},
    handleUser: () => {},
    isAdmin: false,
    handleAdmin: () => {}
});

export function AuthProvider({ children }) {

    const [isAuth, setIsAuth] = useState(false);
    const handleAuth = (state = true) => {
        setIsAuth(state);
    };

    const [user, setUser] = useState({});
    const handleUser = (user) => {
        setUser(user);
    };

    const [isAdmin, setIsAdmin] = useState(false);
    const handleAdmin = (state = true) => {
        setIsAdmin(state);
    };

    return (
        <AuthContext.Provider value={{ isAuth, handleAuth, user, handleUser, isAdmin, handleAdmin }}>
            {children}
        </AuthContext.Provider>
    );
}

export const useAuth = () => useContext(AuthContext);