class CreateUnionpays < ActiveRecord::Migration
  def change
    create_table :unionpays, id: false do |t|
      t.primary_key :ExternalOrderNo
      t.string :orderid
      t.float :amount
      t.string :currcode
      t.string :memo
      t.string :resptime
      t.integer :status
      t.string :respcode
      t.float :rmbrate
      t.string :sign

      t.timestamps null: false
    end

    add_index :unionpays, :ExternalOrderNo, unique:true
  end
end
