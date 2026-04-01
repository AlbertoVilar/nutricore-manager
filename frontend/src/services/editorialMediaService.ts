import type { MediaUploadResponse } from '../types/editorial';
import { uploadFile } from './httpClient';

export function uploadEditorialImage(file: File) {
  return uploadFile<MediaUploadResponse>('/v1/admin/media/images', file, 'file', {
    auth: true,
  });
}
