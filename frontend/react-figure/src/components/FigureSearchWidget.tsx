import { useEffect, useMemo, useState } from 'react'
import { getFigure } from '../api'
import type { Figure } from '../types'

const inputStyle: React.CSSProperties = {
  padding: '0.6rem 1rem',
  background: '#08060f',
  border: '1px solid #3d2f6b',
  borderRadius: '6px',
  color: '#ffffff',
  fontFamily: 'inherit',
  fontSize: '0.9rem',
  minWidth: '200px',
  outline: 'none',
}

const filtersRowStyle: React.CSSProperties = {
  display: 'flex',
  gap: '1rem',
  flexWrap: 'wrap',
  marginBottom: '1.5rem',
}

// valori distinti e ordinati di un campo, per riempire le tendine
function valoriDistinti(figure: Figure[], campo: 'serie' | 'azienda' | 'materiale'): string[] {
  return Array.from(new Set(figure.map(f => f[campo]).filter(Boolean))).sort()
}

export default function FigureSearchWidget() {
  const [figure, setFigure] = useState<Figure[]>([])
  const [loading, setLoading] = useState<boolean>(true)
  const [errore, setErrore] = useState<string | null>(null)
  const [nome, setNome] = useState<string>('')
  const [serie, setSerie] = useState<string>('')
  const [azienda, setAzienda] = useState<string>('')
  const [materiale, setMateriale] = useState<string>('')

  // La ricerca per nome viene fatta dal backend (GET /api/figure?nome=...),
  // con un piccolo debounce per non sparare una richiesta a ogni tasto premuto.
  useEffect(() => {
    let annullato = false
    const timer = setTimeout(async () => {
      try {
        setLoading(true)
        const dati = await getFigure(nome)
        if (!annullato) {
          setFigure(dati)
          setErrore(null)
        }
      } catch (e) {
        if (!annullato) setErrore('Impossibile caricare le figure.')
      } finally {
        if (!annullato) setLoading(false)
      }
    }, 300)

    return () => {
      annullato = true
      clearTimeout(timer)
    }
  }, [nome])

  const serieDisponibili = useMemo(() => valoriDistinti(figure, 'serie'), [figure])
  const aziendeDisponibili = useMemo(() => valoriDistinti(figure, 'azienda'), [figure])
  const materialiDisponibili = useMemo(() => valoriDistinti(figure, 'materiale'), [figure])

  // I filtri per serie, azienda e materiale sono invece lato client sui risultati gia' ottenuti.
  const figureFiltrate = useMemo(() => {
    return figure.filter(f =>
      (!serie || f.serie === serie) &&
      (!azienda || f.azienda === azienda) &&
      (!materiale || f.materiale === materiale))
  }, [figure, serie, azienda, materiale])

  if (errore) {
    return (
      <p style={{ color: '#f87171', textAlign: 'center', padding: '1rem 0' }}>{errore}</p>
    )
  }

  return (
    <div>
      <div style={filtersRowStyle}>
        <input
          type="text"
          value={nome}
          onChange={e => setNome(e.target.value)}
          placeholder="Cerca per nome..."
          style={inputStyle}
        />
        <select value={serie} onChange={e => setSerie(e.target.value)} style={inputStyle}>
          <option value="">-- Tutte le serie --</option>
          {serieDisponibili.map(s => <option key={s} value={s}>{s}</option>)}
        </select>
        <select value={azienda} onChange={e => setAzienda(e.target.value)} style={inputStyle}>
          <option value="">-- Tutte le aziende --</option>
          {aziendeDisponibili.map(a => <option key={a} value={a}>{a}</option>)}
        </select>
        <select value={materiale} onChange={e => setMateriale(e.target.value)} style={inputStyle}>
          <option value="">-- Tutti i materiali --</option>
          {materialiDisponibili.map(m => <option key={m} value={m}>{m}</option>)}
        </select>
      </div>

      {loading && (
        <p style={{ color: 'rgba(255,255,255,0.7)', textAlign: 'center', padding: '2rem 0' }}>
          Caricamento...
        </p>
      )}

      {!loading && figureFiltrate.length === 0 && (
        <div className="empty-state">
          <p>Nessuna figure corrisponde ai criteri di ricerca.</p>
        </div>
      )}

      {!loading && figureFiltrate.length > 0 && (
        <table className="data-table">
          <thead>
            <tr>
              <th>Nome</th>
              <th>Serie</th>
              <th>Azienda</th>
              <th>Materiale</th>
              <th style={{ textAlign: 'right' }}>Altezza</th>
              <th style={{ textAlign: 'right' }}>Prezzo</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {figureFiltrate.map(f => (
              <tr key={f.id}>
                <td>{f.nome}</td>
                <td>{f.serie}</td>
                <td>{f.azienda}</td>
                <td>{f.materiale}</td>
                <td style={{ textAlign: 'right' }}>{f.altezza} cm</td>
                <td style={{ textAlign: 'right' }}>{f.prezzo.toFixed(2)} EUR</td>
                <td>
                  <a href={`/figure/${f.id}`} className="btn-small">Dettagli</a>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  )
}
