import axios from 'axios'
import type { Figure } from './types'

function baseUrl(): string {
  const el = document.getElementById('figure-react-root')
  return el?.dataset?.apiBase ?? '/api'
}

export const api = axios.create({
  baseURL: baseUrl(),
  timeout: 10_000,
})

export async function getFigure(nome?: string): Promise<Figure[]> {
  const { data } = await api.get<Figure[]>('/figure', {
    params: nome && nome.trim() !== '' ? { nome } : undefined,
  })
  return data
}
