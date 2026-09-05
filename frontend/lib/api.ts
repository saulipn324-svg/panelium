export type WorkCard={id:number;slug:string;title:string;author:string;format:string;status:string;coverKey:string;accent:string};
export type Chapter={id:number;number:string;title:string;pageCount:number;direction:string};
export type WorkDetail=WorkCard&{synopsis:string;chapters:Chapter[]};
const base=process.env.PANELIUM_API_URL??"http://localhost:8080";
export async function api<T>(path:string,init?:RequestInit):Promise<T>{const r=await fetch(base+path,{...init,headers:{"Content-Type":"application/json",...(init?.headers??{})},cache:"no-store"});if(!r.ok)throw new Error(`API ${r.status}`);return r.json();}
