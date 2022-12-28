import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-awards-form',
  templateUrl: './awards-form.component.html',
  styleUrls: ['./awards-form.component.scss']
})
export class AwardsFormComponent implements OnInit {
  @Input() items: { award: string, year: string, description: string }[] = [
    { award: '', year: '', description: ''}
  ];
  @Output() onChange: EventEmitter<{ award: string, year: string, description: string }[]> = new EventEmitter<{ award: string, year: string, description: string }[]>();

  constructor() { }

  public ngOnInit(): void {
  }

  public addSlot(): void {
    if (!this.verify()) {
      alert('The slot you add already exist');
      return;
    }
    const item: { award: string, year: string, description: string } = {
      award: '',
      year: '',
      description: ''
    };
    this.onChange.emit(this.items);
    this.items.push(item);
  }

  public verify(): boolean {
    if (this.items && this.items.length > 0) {
      const lastSlot = this.items[this.items.length - 1];
      if (lastSlot.award === '' || lastSlot.year === '' || lastSlot.description === '') {
        return false;
      }
      for (let i = 0; i < this.items.length - 1; i++) {
        const item = this.items[i];
        if (item.award === lastSlot.award && item.year === lastSlot.year && item.description === lastSlot.description) {
          return false;
        }
      }
      return true;
    }
    return true;
  }

  public remove(item: { award: string, year: string, description: string }): void {
    const index: number = this.items.findIndex(s => (s === item));
    if (index !== -1) {
      this.items.splice(index, 1);
      this.saveChanges();
      return;
    }
    return;
  }

  public saveChanges(): void {
    this.items.forEach((slot, i) => {
      if (slot.award === '' || slot.year === '') {
        this.items.splice(i, 1);
      }
    });
    let items: { award: string, year: string, description: string }[] = [];
    items.push(...this.items);
    items = items.filter((value, index, self) =>
      index === self.findIndex((t) => (
        t.award === value.award && t.year === value.year && t.description === value.description
      ))
    );
    this.items = items;
    this.onChange.emit(this.items);
  }
}
