type HomeProps = {
    onLogout: () => void;
};

export default function Home({onLogout}: HomeProps) {
    const handleLogout = () => {
        const ok = confirm("로그아웃 하시겠습니까?");
        if (ok) {
            onLogout();
        }
    }

    return (
        <div>
            <h1>Home</h1>
            <button className={"medium primary"} onClick={handleLogout}>로그아웃</button>
        </div>
    )
}