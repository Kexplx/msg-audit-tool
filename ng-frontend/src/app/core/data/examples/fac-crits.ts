import { FacCrit } from '../models/faccrit.model';
import { generate } from 'shortid';

export const FACCRITS: FacCrit[] = [
  { id: '1', name: 'FACTOR 1', referenceId: null },

  { id: '2', name: 'FACTOR 2', referenceId: null },

  { id: '3', name: 'FACTOR 3', referenceId: null },
  { id: generate(), name: 'CRITERIA 1', referenceId: '3' },
  { id: generate(), name: 'CRITERIA 2', referenceId: '3' },
  { id: generate(), name: 'CRITERIA 3', referenceId: '3' },

  { id: '4', name: 'FACTOR 4', referenceId: null },
  { id: generate(), name: 'CRITERIA 1', referenceId: '4' },
  { id: generate(), name: 'CRITERIA 2', referenceId: '4' },
  { id: generate(), name: 'CRITERIA 4', referenceId: '4' },
];
