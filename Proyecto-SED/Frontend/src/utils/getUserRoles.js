export default async function getUserRoles() {
    try {
        const response = await fetch(`${import.meta.env.VITE_API_URL}/api/auth`, {
            method: 'GET',
            credentials: 'include'
        });
        
        if (response.ok) {
            const data = await response.json();
            return data.role;
        } else {
            return { error: 'Unable to fetch roles' };
        }
    } catch (error) {
        return { error: error.message };
    }
}