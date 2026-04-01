import { EditorialSessionProvider } from './hooks/useEditorialSession';
import { PublicSiteDataProvider } from './hooks/usePublicSiteData';
import { AppRoutes } from './routes/AppRoutes';

function App() {
  return (
    <EditorialSessionProvider>
      <PublicSiteDataProvider>
        <AppRoutes />
      </PublicSiteDataProvider>
    </EditorialSessionProvider>
  );
}

export default App;
