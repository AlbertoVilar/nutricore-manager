import type { MediaUploadResponse } from '../types/editorial';
import { uploadFile } from './httpClient';
import { buildEditorialHeaders } from './editorialRequest';

export function uploadEditorialImage(token: string, file: File) {
  return uploadFile<MediaUploadResponse>('/v1/admin/media/images', file, 'file', {
    headers: buildEditorialHeaders(token),
  });
}
