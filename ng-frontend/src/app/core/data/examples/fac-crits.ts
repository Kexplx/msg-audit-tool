import { FacCrit } from '../models/faccrit.model';
import { generate } from 'shortid';

export const FACCRITS: FacCrit[] = [
  // {
  //   id: '1',
  //   name: 'FACTOR 1',
  //   referenceId: null,
  //   questions: [
  //     { facCritId: '1', id: generate(), textDe: 'FACTOR 1 FRAGE 1' },
  //     { facCritId: '1', id: generate(), textDe: 'FACTOR 1 FRAGE 2' },
  //     { facCritId: '1', id: generate(), textDe: 'FACTOR 1 FRAGE 3' },
  //     { facCritId: '1', id: generate(), textDe: 'FACTOR 1 FRAGE 4' },
  //     { facCritId: '1', id: generate(), textDe: 'FACTOR 1 FRAGE 5' },
  //     { facCritId: '1', id: generate(), textDe: 'FACTOR 1 FRAGE 6' },
  //     { facCritId: '1', id: generate(), textDe: 'FACTOR 1 FRAGE 7' },
  //   ],
  // },
  // {
  //   id: '2',
  //   name: 'FACTOR 2',
  //   referenceId: null,
  //   questions: [
  //     { facCritId: '2', id: generate(), textDe: 'FACTOR 2 FRAGE 1' },
  //     { facCritId: '2', id: generate(), textDe: 'FACTOR 2 FRAGE 2' },
  //     { facCritId: '2', id: generate(), textDe: 'FACTOR 2 FRAGE 3' },
  //     { facCritId: '2', id: generate(), textDe: 'FACTOR 2 FRAGE 4' },
  //     { facCritId: '2', id: generate(), textDe: 'FACTOR 2 FRAGE 5' },
  //     { facCritId: '2', id: generate(), textDe: 'FACTOR 2 FRAGE 6' },
  //     { facCritId: '2', id: generate(), textDe: 'FACTOR 2 FRAGE 7' },
  //   ],
  // },
  // {
  //   id: '3',
  //   name: 'FACTOR 3',
  //   referenceId: null,
  //   questions: [
  //     { facCritId: '3', id: generate(), textDe: 'FACTOR 3 FRAGE 1' },
  //     { facCritId: '3', id: generate(), textDe: 'FACTOR 3 FRAGE 2' },
  //     { facCritId: '3', id: generate(), textDe: 'FACTOR 3 FRAGE 3' },
  //     { facCritId: '3', id: generate(), textDe: 'FACTOR 3 FRAGE 4' },
  //     { facCritId: '3', id: generate(), textDe: 'FACTOR 3 FRAGE 5' },
  //     { facCritId: '3', id: generate(), textDe: 'FACTOR 3 FRAGE 6' },
  //     { facCritId: '3', id: generate(), textDe: 'FACTOR 3 FRAGE 7' },
  //   ],
  // },
  // {
  //   id: '213321',
  //   name: 'CRITERIA 1',
  //   referenceId: '3',
  //   questions: [
  //     { facCritId: '213321', id: generate(), textDe: 'CRITERIA 1 FRAGE 1' },
  //     { facCritId: '213321', id: generate(), textDe: 'CRITERIA 1 FRAGE 2' },
  //     { facCritId: '213321', id: generate(), textDe: 'CRITERIA 1 FRAGE 3' },
  //     { facCritId: '213321', id: generate(), textDe: 'CRITERIA 1 FRAGE 4' },
  //     { facCritId: '213321', id: generate(), textDe: 'CRITERIA 1 FRAGE 5' },
  //     { facCritId: '213321', id: generate(), textDe: 'CRITERIA 1 FRAGE 6' },
  //     { facCritId: '213321', id: generate(), textDe: 'CRITERIA 1 FRAGE 7' },
  //   ],
  // },
  // {
  //   id: 'fdaidsf9',
  //   name: 'CRITERIA 2',
  //   referenceId: '3',
  //   questions: [
  //     { facCritId: '213321', id: generate(), textDe: 'CRITERIA 1 FRAGE 1' },
  //     { facCritId: '213321', id: generate(), textDe: 'CRITERIA 1 FRAGE 2' },
  //     { facCritId: '213321', id: generate(), textDe: 'CRITERIA 1 FRAGE 3' },
  //     { facCritId: '213321', id: generate(), textDe: 'CRITERIA 1 FRAGE 4' },
  //     { facCritId: '213321', id: generate(), textDe: 'CRITERIA 1 FRAGE 5' },
  //     { facCritId: '213321', id: generate(), textDe: 'CRITERIA 1 FRAGE 6' },
  //     { facCritId: '213321', id: generate(), textDe: 'CRITERIA 1 FRAGE 7' },
  //   ],
  // },
  // {
  //   id: 'dasadssad92',
  //   name: 'CRITERIA 3',
  //   referenceId: '3',
  //   questions: [
  //     { facCritId: 'dasadssad92', id: generate(), textDe: 'CRITERIA 1 FRAGE 1' },
  //     { facCritId: 'dasadssad92', id: generate(), textDe: 'CRITERIA 1 FRAGE 2' },
  //     { facCritId: 'dasadssad92', id: generate(), textDe: 'CRITERIA 1 FRAGE 3' },
  //     { facCritId: 'dasadssad92', id: generate(), textDe: 'CRITERIA 1 FRAGE 4' },
  //     { facCritId: 'dasadssad92', id: generate(), textDe: 'CRITERIA 1 FRAGE 5' },
  //     { facCritId: 'dasadssad92', id: generate(), textDe: 'CRITERIA 1 FRAGE 6' },
  //     { facCritId: 'dasadssad92', id: generate(), textDe: 'CRITERIA 1 FRAGE 7' },
  //   ],
  // },
  // {
  //   id: '4',
  //   name: 'FACTOR 4',
  //   referenceId: null,
  //   questions: [
  //     { facCritId: '4', id: generate(), textDe: 'FACTOR 4 FRAGE 1' },
  //     { facCritId: '4', id: generate(), textDe: 'FACTOR 4 FRAGE 2' },
  //     { facCritId: '4', id: generate(), textDe: 'FACTOR 4 FRAGE 3' },
  //     { facCritId: '4', id: generate(), textDe: 'FACTOR 4 FRAGE 4' },
  //     { facCritId: '4', id: generate(), textDe: 'FACTOR 4 FRAGE 5' },
  //     { facCritId: '4', id: generate(), textDe: 'FACTOR 4 FRAGE 6' },
  //     { facCritId: '4', id: generate(), textDe: 'FACTOR 4 FRAGE 7' },
  //   ],
  // },
  // {
  //   id: '32899823',
  //   name: 'CRITERIA 1',
  //   referenceId: '4',
  //   questions: [
  //     { facCritId: '32899823', id: generate(), textDe: 'CRITERIA 1 FRAGE 1' },
  //     { facCritId: '32899823', id: generate(), textDe: 'CRITERIA 1 FRAGE 2' },
  //     { facCritId: '32899823', id: generate(), textDe: 'CRITERIA 1 FRAGE 3' },
  //     { facCritId: '32899823', id: generate(), textDe: 'CRITERIA 1 FRAGE 4' },
  //     { facCritId: '32899823', id: generate(), textDe: 'CRITERIA 1 FRAGE 5' },
  //     { facCritId: '32899823', id: generate(), textDe: 'CRITERIA 1 FRAGE 6' },
  //     { facCritId: '32899823', id: generate(), textDe: 'CRITERIA 1 FRAGE 7' },
  //   ],
  // },
  // {
  //   id: '1299d9as0ß',
  //   name: 'CRITERIA 2',
  //   referenceId: '4',
  //   questions: [
  //     { facCritId: '1299d9as0ß', id: generate(), textDe: 'CRITERIA 2 FRAGE 1' },
  //     { facCritId: '1299d9as0ß', id: generate(), textDe: 'CRITERIA 2 FRAGE 2' },
  //     { facCritId: '1299d9as0ß', id: generate(), textDe: 'CRITERIA 2 FRAGE 3' },
  //     { facCritId: '1299d9as0ß', id: generate(), textDe: 'CRITERIA 2 FRAGE 4' },
  //     { facCritId: '1299d9as0ß', id: generate(), textDe: 'CRITERIA 2 FRAGE 5' },
  //     { facCritId: '1299d9as0ß', id: generate(), textDe: 'CRITERIA 2 FRAGE 6' },
  //     { facCritId: '1299d9as0ß', id: generate(), textDe: 'CRITERIA 2 FRAGE 7' },
  //   ],
  // },
  // {
  //   id: 'ds80m290sdas',
  //   name: 'CRITERIA 3',
  //   referenceId: '4',
  //   questions: [
  //     { facCritId: 'ds80m290sdas', id: generate(), textDe: 'CRITERIA 3 FRAGE 1' },
  //     { facCritId: 'ds80m290sdas', id: generate(), textDe: 'CRITERIA 3 FRAGE 2' },
  //     { facCritId: 'ds80m290sdas', id: generate(), textDe: 'CRITERIA 3 FRAGE 3' },
  //     { facCritId: 'ds80m290sdas', id: generate(), textDe: 'CRITERIA 3 FRAGE 4' },
  //     { facCritId: 'ds80m290sdas', id: generate(), textDe: 'CRITERIA 3 FRAGE 5' },
  //     { facCritId: 'ds80m290sdas', id: generate(), textDe: 'CRITERIA 3 FRAGE 6' },
  //     { facCritId: 'ds80m290sdas', id: generate(), textDe: 'CRITERIA 3 FRAGE 7' },
  //   ],
  // },
];
