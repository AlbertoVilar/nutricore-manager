import {
  createContext,
  useCallback,
  useContext,
  useEffect,
  useMemo,
  useState,
  type ReactNode,
} from 'react';
import {
  getPublicArticles,
  getPublicPlans,
  getPublicPosts,
  getPublicProfile,
  getPublicRecipes,
} from '../services/publicContentService';
import type { PublicArticle, PublicPlan, PublicPost, PublicProfile, PublicRecipe } from '../types/public-content';

interface PublicSiteDataContextValue {
  profile: PublicProfile | null;
  plans: PublicPlan[];
  articles: PublicArticle[];
  posts: PublicPost[];
  recipes: PublicRecipe[];
  isLoading: boolean;
  errors: string[];
  refresh: () => Promise<void>;
}

const PublicSiteDataContext = createContext<PublicSiteDataContextValue | undefined>(undefined);

interface PublicSiteDataProviderProps {
  children: ReactNode;
}

export function PublicSiteDataProvider({ children }: PublicSiteDataProviderProps) {
  const [profile, setProfile] = useState<PublicProfile | null>(null);
  const [plans, setPlans] = useState<PublicPlan[]>([]);
  const [articles, setArticles] = useState<PublicArticle[]>([]);
  const [posts, setPosts] = useState<PublicPost[]>([]);
  const [recipes, setRecipes] = useState<PublicRecipe[]>([]);
  const [errors, setErrors] = useState<string[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  const refresh = useCallback(async () => {
    setIsLoading(true);

    const [profileResult, plansResult, articlesResult, postsResult, recipesResult] = await Promise.allSettled([
      getPublicProfile(),
      getPublicPlans(),
      getPublicArticles(),
      getPublicPosts(),
      getPublicRecipes(),
    ]);

    const nextErrors: string[] = [];

    if (profileResult.status === 'fulfilled') {
      setProfile(profileResult.value);
    } else {
      setProfile(null);
      nextErrors.push('Nao foi possivel carregar o perfil publico.');
    }

    if (plansResult.status === 'fulfilled') {
      setPlans(plansResult.value);
    } else {
      setPlans([]);
      nextErrors.push('Nao foi possivel carregar os planos de atendimento.');
    }

    if (articlesResult.status === 'fulfilled') {
      setArticles(articlesResult.value);
    } else {
      setArticles([]);
      nextErrors.push('Nao foi possivel carregar os artigos publicados.');
    }

    if (postsResult.status === 'fulfilled') {
      setPosts(postsResult.value);
    } else {
      setPosts([]);
      nextErrors.push('Nao foi possivel carregar os conteudos publicados.');
    }

    if (recipesResult.status === 'fulfilled') {
      setRecipes(recipesResult.value);
    } else {
      setRecipes([]);
      nextErrors.push('Nao foi possivel carregar as receitas.');
    }

    setErrors(nextErrors);
    setIsLoading(false);
  }, []);

  useEffect(() => {
    void refresh();
  }, [refresh]);

  const value = useMemo<PublicSiteDataContextValue>(
    () => ({
        profile,
        plans,
        articles,
        posts,
        recipes,
        isLoading,
        errors,
        refresh,
      }),
    [articles, errors, isLoading, plans, posts, profile, recipes, refresh],
  );

  return <PublicSiteDataContext.Provider value={value}>{children}</PublicSiteDataContext.Provider>;
}

export function usePublicSiteData() {
  const context = useContext(PublicSiteDataContext);

  if (!context) {
    throw new Error('usePublicSiteData must be used within PublicSiteDataProvider');
  }

  return context;
}
