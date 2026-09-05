"use client";
import Link from "next/link";import {useEffect,useState} from "react";
type User={name:string;email:string;role:string};
export default function SessionNav(){const[user,setUser]=useState<User|null>(null);useEffect(()=>{const raw=localStorage.getItem("panelium_user");if(raw)setUser(JSON.parse(raw))},[]);if(!user)return <Link className="session-link" href="/login">Iniciar sesión</Link>;return <div className="session"><span>{user.name}</span>{user.role==="ADMIN"&&<Link href="/admin">Administrar</Link>}<button onClick={()=>{localStorage.removeItem("panelium_token");localStorage.removeItem("panelium_user");location.href="/"}}>Salir</button></div>}
