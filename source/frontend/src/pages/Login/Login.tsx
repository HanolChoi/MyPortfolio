import {type FormEvent, useState } from 'react'
import Button from '../../components/Button/Button'

type LoginProps = {
  onLogin: () => void;
}

type LoginResponse = {
    accessToken: string;
}

export default function Login({onLogin}: LoginProps) {
    const [id, setId] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessages, setErrorMessages] = useState('');
    const [isLoading, setIsLoading] = useState(false);

    async function handleLogin(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
        setErrorMessages('');
        setIsLoading(true);

        try {
            const response = await fetch('http://localhost:8080/api/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include',
                body: JSON.stringify({ userId: id, password }),
            });

            if (!response.ok) {
                throw new Error('로그인에 실패했습니다.');
            }

            const data: LoginResponse = await response.json();

            localStorage.setItem('accessToken', data.accessToken);
            onLogin();
        } catch {
            setErrorMessages('아이디 또는 비밀번호를 확인해주세요.');
        } finally {
            setIsLoading(false);
        }
    }

    return (
        <section id={"loginForm"}>
            <h1 className={"login-form__title"}>로그인</h1>
            <p className={"login-form__description"}>접속을 환영합니다.<br />로그인을 위해 아래 계정을 입력해주세요.</p>

            <form onSubmit={handleLogin}>
                <label>
                    <input value={id} autoComplete="id" placeholder={"아이디"} type={"id"} onChange={(event) => setId(event.target.value)} />
                </label>
                <label>
                    <input value={password} autoComplete="current-password" placeholder={"비밀번호"} type={"password"} onChange={(event) => setPassword(event.target.value)} />
                </label>

                {errorMessages && <p>{errorMessages}</p>}

                <Button disabled={isLoading} size={"medium"} type={"submit"} variant={"primary"}>{isLoading ? '로그인중...' : '로그인'}</Button>
                <Button disabled={isLoading} size={"medium"} type={"submit"} variant={"ghost"}>{isLoading ? '로그인중...' : '로그인'}</Button>
                <Button disabled={isLoading} size={"medium"} type={"submit"} variant={"outline"}>{isLoading ? '로그인중...' : '로그인'}</Button>
            </form>
        </section>
    )
}