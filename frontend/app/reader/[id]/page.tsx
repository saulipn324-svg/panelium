import Reader from "./reader";import {api} from "@/lib/api";
export type Manifest={chapterId:number;workId:number;workTitle:string;chapterTitle:string;direction:string;pages:string[]};
export default async function Page({params,searchParams}:{params:Promise<{id:string}>;searchParams:Promise<{page?:string}>}){const {id}=await params;const {page}=await searchParams;const manifest=await api<Manifest>(`/api/chapters/${id}/manifest`);return <Reader manifest={manifest} initialPage={Math.max(1,Number(page)||1)}/>}
