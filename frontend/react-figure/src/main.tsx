import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import FigureSearchWidget from './components/FigureSearchWidget'

const mount = document.getElementById('figure-react-root')

if (mount) {
  createRoot(mount).render(
    <StrictMode>
      <FigureSearchWidget />
    </StrictMode>,
  )
} else {
  console.warn('[figure-react] mount point #figure-react-root non trovato')
}
