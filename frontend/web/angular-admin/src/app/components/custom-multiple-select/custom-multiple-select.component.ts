import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { MultiselecItem } from 'src/app/models';

/**
 * @author Maestros
 * @email roslyn.temateu@dimsoft.eu
 */
@Component({
  selector: 'app-custom-multiple-select',
  templateUrl: './custom-multiple-select.component.html',
  styleUrls: ['./custom-multiple-select.component.scss']
})
export class CustomMultipleSelectComponent implements OnInit, OnChanges {

  @Input() inputValue: string = '';
  @Input() inputPlaceholder: string = '';
  @Input() machtList: MultiselecItem[] = [];
  @Input() selectedItems: MultiselecItem[] = [];
  @Input() isDropDownOpen: boolean = false;

  @Output() selectOptionEvent = new EventEmitter<MultiselecItem>();
  @Output() deleteOptionEvent = new EventEmitter<MultiselecItem>();
  @Output() typingInputEvent = new EventEmitter<string>();

  constructor() { }

  ngOnChanges(changes: SimpleChanges): void {
  }
  ngOnInit() { }



  /**
   *
   * @param title
   * @param itemIndex
   * @returns
   */
  removeItem = (title: string | undefined, itemIndex: number | undefined): void => this.deleteOptionEvent.next({ id: itemIndex, title: title, selected: true })

  /**
   *
   * @param id
   */
  addItem = (item: MultiselecItem): void => this.selectOptionEvent.next(item);

  /**
   *
   * @returns
   */
  isEmptyMatchList = (): boolean => this.machtList.length === 0;

  /**
   *
   * @returns
   */
  isEmptySelectedItems = (): boolean => this.selectedItems.length === 0;

  /**
   *
   * @param event
   * @returns
   */
  getMatchList = (event: any): void => this.typingInputEvent.emit(event.target.value);
}
