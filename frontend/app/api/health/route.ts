import { NextResponse } from "next/server";

export async function GET() {
  const base = process.env.PANELIUM_API_URL ?? "http://localhost:8080";

  try {
    const response = await fetch(`${base}/actuator/health`, { cache: "no-store" });
    const health = await response.json();
    return NextResponse.json(health, { status: response.status });
  } catch {
    return NextResponse.json({ status: "DOWN" }, { status: 503 });
  }
}
