import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.scss'

// 👉 Add these two lines below your global styles
import "slick-carousel/slick/slick.css"
import "slick-carousel/slick/slick-theme.css"

import App from './App.tsx'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <App />
  </StrictMode>,
)
