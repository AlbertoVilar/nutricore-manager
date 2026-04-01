import { PublicSiteDataProvider } from './hooks/usePublicSiteData';
import { AppRoutes } from './routes/AppRoutes';

function App() {
  return (
    <PublicSiteDataProvider>
      <AppRoutes />
    </PublicSiteDataProvider>
  );
}

export default App;
