module Send_API

  extend ActiveSupport::Concern

  require 'net/http'
  require 'digest'

  def get_product(id)
    @key_str = 'kikirace'
    set_time_key

    uri = URI('http://kikistore.csmuse.com/kikistore/api/kikirace_getProductDetail.php')
    params = { :ProductSN => id, :Language => 2, :Time => @unix_time, :Key => @key }
    uri.query = URI.encode_www_form(params)

    res = Net::HTTP.get(uri).force_encoding("UTF-8")
    hash = JSON.parse(res[2..-1].to_s)

    hash['Product'][0]['SellPriceCNY'] = hash['Product'][0]['SellPriceCNY'].ceil.to_i
    hash['Product'][0]['ProductIntroduction'] = hash['Product'][0]['ProductIntroduction'].gsub(/<br>/, "\n")

    photos = ['LargeIcon', 'SmallIcon']
    (1..8).each { |num| photos << 'ProductPhoto' + num.to_s }
    photos.each do |photo|
      hash['Product'][0][photo] = 'none' if hash['Product'][0][photo] == 'http://kikistore.csmuse.com/kikistore/upload/product/'
    end

    hash
  end

  def create_order(data)
    @key_str = 'kikirace'
    set_time_key

    uri = URI('http://kikistore.csmuse.com/kikistore/api/kikirace_createOrder.php')
    params = { :Username => 'B123070146', :Password => 'b03902107', :Time => @unix_time, :Key => @key }.merge(data)
    uri.query = URI.encode_www_form(params)

    res = Net::HTTP.get(uri).force_encoding("UTF-8")
    hash = JSON.parse(res[2..-1].to_s)
  end

  def query_order
    @key_str = 'kikirace'
    set_time_key

    uri = URI('http://kikistore.csmuse.com/kikistore/api/kikirace_queryOrder.php')
    params = { :Username => 'B123070146', :Password => 'b03902107', :Time => @unix_time, :Key => @key }
    uri.query = URI.encode_www_form(params)

    res = Net::HTTP.get(uri).force_encoding("UTF-8")
    hash = JSON.parse(res.to_s)
  end

  def query_order_by_external_order_no(id)
    hash = query_order

    hash['Order'].select! { |order| order['ExternalOrderNo'] == id }

    hash
  end

  def cancel_order(id)
    result = query_order_by_external_order_no(id)
    if result['ErrorCode'] == 1
      return result
    end

    if result['Order'].empty?
      result['ErrorCode'] = 1
      result['ErrorMsg'] = '無此編號'
      return result
    end

    orderNo = result['Order'][0]['OrderNo']

    @key_str = 'kikistore'
    set_time_key

    uri = URI('http://kikistore.csmuse.com/kikistore/api/cancelOrder.php')
    params = { :Username => 'B123070146', :Password => 'b03902107', :OrderNo => orderNo, :Time => @unix_time, :Key => @key }
    uri.query = URI.encode_www_form(params)

    res = Net::HTTP.get(uri).force_encoding("UTF-8")
    hash = JSON.parse(res.to_s)
  end

  def set_time_key
    @unix_time = Time.now.to_i

    md5 = Digest::MD5.new
    md5.update @unix_time.to_s + @key_str
    @key = md5.hexdigest
  end

end
