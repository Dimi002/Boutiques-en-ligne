import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { AbstractControl, FormControl, FormGroup } from '@angular/forms';
import { Editor, Toolbar, Validators } from 'ngx-editor';

/**
 * @author Maestros
 * @email roslyn.temateur@dimsoft.eu
 */
@Component({
  selector: 'app-reach-text',
  templateUrl: './reach-text.component.html',
  styleUrls: ['./reach-text.component.scss']
})
export class ReachTextComponent implements OnInit {

  @Input()
  public placeholder: string = '';
  @Input()
  public editor: Editor = new Editor();
  @Input()
  public toolbar: Toolbar = [];
  @Input()
  public colorPresets: string[] = [];
  @Input()
  public outputValue: string = '';

  @Output()
  private emitReachTextContent: EventEmitter<string> = new EventEmitter<string>();

  public outputFormat: 'doc' | 'html' = 'html';

  constructor() { }


  get doc(): AbstractControl | null {
    return this.form.get('longDescription');
  }

  form = new FormGroup({
    longDescription: new FormControl(
      { value: '', disabled: false },
      Validators.required()
    ),
  });

  ngOnInit(): void { }

  public getHTML = () => {
    const emitData: string = this.form.get('longDescription')?.value!
    this.emitReachTextContent.emit(emitData)
  }

}
